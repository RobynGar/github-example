package connectors

import models.APIError.BadAPIResponse
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json, OFormat}
import play.api.libs.ws.{WSAuthScheme, WSClient, WSResponse}
import models.{APIError, Content, CreateFile, DeletedReturn, FFitems, File, Repository, RequestDelete, ReturnCreatedFile, UpdatedFile, User}
import play.api.http.Status
import play.api.http.Status.{NOT_FOUND, OK}
import play.api.mvc.Request

import java.util.Base64
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.sys.env
import scala.util.Right

class ApplicationConnector @Inject()(ws: WSClient) {
  def get[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, User]] = {
    val request = ws.url(url).get()
    request.map {
      result =>
        val gitUser = result.json
        val login = (gitUser \ "login").as[String]
        val created = (gitUser \ "created_at").as[String]
        val location = (gitUser \ "location").asOpt[String]
        val followers = (gitUser \ "followers").as[Int]
        val following = (gitUser \ "following").as[Int]
        Right(User(login, created, location, followers, following))
    }
      .recover {
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find user"))
      }
  }

  def getUserRepo[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, List[String]]] = {
    val request = ws.url(url).get()
    request.map {
      result =>

        val allRepos = result.json
        val allRepoNames = (allRepos \\ "name").map(_.as[String])
        val repoOwner = (allRepos.head \ "owner" \ "login").as[String]
        Right(repoOwner +: allRepoNames.toList)
    }
      .recover {
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))
      }
  }


  def getRepoContent[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, Seq[FFitems]]] = {
    val request = ws.url(url).get()
    request.map {
      result =>
        val response = result.json
        val seqOfFolderFiles = response.as[Seq[FFitems]]
        Right(seqOfFolderFiles)

    }
      .recover {
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repository files"))
      }
  }

  def getDirContent[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, Seq[FFitems]]] = {
    val request = ws.url(url).get()
    request.map {
      result =>
        val response = result.json
        val seqOfFolderFiles = response.as[Seq[FFitems]]
        Right(seqOfFolderFiles)

    }
      .recover {
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repository files"))
      }
  }

  def getFileContent[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, File]] = {
      val request = ws.url(url).get()
      request.map {
        result =>
          result.json.validate[File] match {
            case JsSuccess(value, _) => Right(File(value.name, value.sha, value.fType, value.path, value.url, value.download_url, value.content, Base64.getMimeDecoder().decode(value.content).map(_.toChar).mkString))
            //new String(Base64.getDecoder.decode(value.content.replaceAll("\n", ""))) other option for the decoded byte string in file parameter
            //OR new String(Base64.getMimeDecoder().decode(value.content))
            case JsError(errors) => Left(APIError.BadAPIResponse(400, "could not find any file contents"))
          }
      }

    }


  def getUserRepoInfo[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, Repository]] = {
    val request = ws.url(url).get()
    request.map {
      result =>
        val gitRepo = result.json
        val owner = (gitRepo \ "owner" \ "login").as[String]
        val name = (gitRepo \ "name").as[String]
        val privateStatus = (gitRepo \ "private").as[Boolean]
        val repoURL = (gitRepo \ "url").as[String]
        val htmlURL = (gitRepo \ "html_url").as[String]
        Right(Repository(owner, name, privateStatus, repoURL, htmlURL))
    }
      .recover {
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repositories information"))
      }
  }

  def createFile[Response](newFile: CreateFile, url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, ReturnCreatedFile]] = {
    val password = env.get("AuthPassword") match {
      case Some(token) => token
      case None => "error Auth token not found"
    }
    //can also use this instead of .getOrElse for the
    val addFile = CreateFile(newFile.message, Base64.getEncoder.encodeToString(newFile.content.getBytes()))
    val request = ws.url(url)
      .withHttpHeaders("Accept" -> "application/vnd.github+json")
      .withHttpHeaders("Authorization" -> s"token $password")
    //${password.getOrElse("error no token")}
    val response: Future[WSResponse] = request
      .put(CreateFile.formats.writes(addFile))

    response.map {
      result =>
        result.json.validate[ReturnCreatedFile] match {
          case JsSuccess(value, _) => Right(value)
          case JsError(errors) =>
            //            println(Json.prettyPrint(result.json))
            //            println(s"moose ${errors}")
            Left(APIError.BadAPIResponse(400, "could not find create file"))
        }

    }
  }


  def updateFile[Response](updatedFile: CreateFile, url: String, sha: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, ReturnCreatedFile]] = {
    val password = env.getOrElse("AuthPassword", "no Auth token found")
    val addFile = UpdatedFile(updatedFile.message, Base64.getEncoder.encodeToString(updatedFile.content.getBytes()), sha)
    val request = ws.url(url)
      .withHttpHeaders("Accept" -> "application/vnd.github+json")
      .withHttpHeaders("Authorization" -> s"token $password")
    val response: Future[WSResponse] = request
      .put(UpdatedFile.formats.writes(addFile))

    response.map {
      result =>
        result.json.validate[ReturnCreatedFile] match {
          case JsSuccess(value, _) => Right(value)
          case JsError(errors) => Left(APIError.BadAPIResponse(400, "could not update file"))
        }
    }
  }

  def deleteFile[Response](url: String, deleteRequest: RequestDelete)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, String]] = {
    val password = env.getOrElse("AuthPassword", "no Auth token found")
    val request = ws.url(url)
      .withHttpHeaders("Accept" -> "application/vnd.github+json")
      .withHttpHeaders("Authorization" -> s"token $password")
      .withBody(RequestDelete.formats.writes(deleteRequest))
    val response: Future[WSResponse] = request.execute("DELETE") //.delete()

    response.map {
      result =>
        result.status match {
          case OK =>
            Right("DELETED")
//            result.json.validate[DeletedReturn] match {
//            case JsSuccess(deleted, _) => Right(deleted)
//            case _ =>
//              println(Json.prettyPrint(result.json))
//              Left(APIError.BadAPIResponse(500, "could not delete file"))
//          }
          case _ => Left(APIError.BadAPIResponse(404, "could not delete file"))
        }
    }
  }

  def repoReadMe[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, File]] = {
    val password = env.getOrElse("AuthPassword", "no Auth token found")
    val request = ws.url(url)
      .withHttpHeaders("Accept" -> "application/vnd.github+json")
      .withHttpHeaders("Authorization" -> s"token $password")
    val response= request.get()
    response.map {
      result =>
        result.json.validate[File] match {
          case JsSuccess(value, _) => Right(File(value.name, value.sha, value.fType, value.path, value.url, value.download_url, value.content, Base64.getMimeDecoder().decode(value.content).map(_.toChar).mkString))
          case JsError(errors) => Left(APIError.BadAPIResponse(400, "could not find readme for this repository"))
        }
    }
  }

  def dirReadMe[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, File]] = {
    val password = env.getOrElse("AuthPassword", "no Auth token found")
    val request = ws.url(url)
      .withHttpHeaders("Accept" -> "application/vnd.github+json")
      .withHttpHeaders("Authorization" -> s"token $password")
    val response= request.get()
    response.map {
      result =>
        result.status match {
          case OK => result.json.validate[File] match {
            case JsSuccess(value, _) => Right(File(value.name, value.sha, value.fType, value.path, value.url, value.download_url, value.content, Base64.getMimeDecoder().decode(value.content).map(_.toChar).mkString))
            case JsError(errors) => Left(APIError.BadAPIResponse(400, "could not validate as file"))
          }
          case NOT_FOUND => Left(APIError.BadAPIResponse(404, "resource not found"))
          case Status.UNPROCESSABLE_ENTITY => Left(APIError.BadAPIResponse(422, "validation failed"))
          case _ => Left(APIError.BadAPIResponse(400, "could not find a readme file for this directory"))
        }
    }
  }

  def downloadTar[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, Int]] ={
    val password = env.getOrElse("AuthPassword", "no Auth token found")
    val request = ws.url(url)
      .withHttpHeaders("Accept" -> "application/vnd.github+json")
      .withHttpHeaders("Authorization" -> s"token $password")
    val response= request.get()
    response.map {
      result =>
        result.status match {
          case Status.FOUND | OK => Right(302)
          case _ => Left(APIError.BadAPIResponse(400, "could not download tar"))
        }
    }
  }

  def downloadZip[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, Int]] ={
    val password = env.getOrElse("AuthPassword", "no Auth token found")
    val request = ws.url(url)
      .withHttpHeaders("Accept" -> "application/vnd.github+json")
      .withHttpHeaders("Authorization" -> s"token $password")
    val response= request.get()
    response.map {
      result =>
        result.status match {
          case Status.FOUND | OK => Right(302)
          case _ => Left(APIError.BadAPIResponse(400, "could not download zip"))
        }
    }
  }


}

