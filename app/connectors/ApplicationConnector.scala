package connectors

import models.APIError.BadAPIResponse
import play.api.libs.json.{JsError, JsSuccess, OFormat}
import play.api.libs.ws.{WSClient, WSResponse}
import models.{APIError, Repository, User}
import play.api.http.Status

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

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
        Right(allRepoNames.toList)


//        result.json.validate[ListRepositories] match {
//          case JsSuccess(value, _) =>
//            Right(Repository(value.name.head, value.privateStatus.head, value.repoURL.head, value.html_url.head))
//          case JsError(errors) =>
//            Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))
//
//        }
  }
      .recover{
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))
      }
  }

    //def getRepoContent[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext) = {
    //    val request = ws.url(url).get()
    //    request.map {
    //      result =>
    //        val gitRepo = result.json
    //  }


  def getRepoContent[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Either[APIError, List[String]]] = {
    val request = ws.url(url).get()
    request.map {
      result =>
        val allFiles = result.json
        val allFileNames = (allFiles \\ "name").map(_.as[String])
        Right(allFileNames.toList)

    }
      .recover{
        case _ =>
          Left(APIError.BadAPIResponse(400, "could not find any repository files"))
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

