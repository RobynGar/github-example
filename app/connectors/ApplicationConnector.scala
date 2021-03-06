package connectors

import models.APIError.BadAPIResponse
import play.api.libs.json.{JsError, JsSuccess, OFormat}
import play.api.libs.ws.{WSClient, WSResponse}
import models.{APIError, FFitems, File, Repository, User}
import play.api.http.Status
import java.util.Base64

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
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
      .recover{
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))
      }
  }



  def getRepoContent[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, Seq[FFitems]]] = {
    val request = ws.url(url).get()
    request.map {
      result =>
//        val allFiles = result.json
//        val allFileNames = (allFiles \\ "name").map(_.as[String])
//        Right(allFileNames.toList)
        val response = result.json
        val seqOfFolderFiles = response.as[Seq[FFitems]]
        Right(seqOfFolderFiles)

    }
      .recover{
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
      .recover{
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
      .recover{
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repositories information"))
      }
  }

}

