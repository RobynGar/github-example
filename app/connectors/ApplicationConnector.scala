package connectors

import models.APIError.BadAPIResponse
import play.api.libs.json.OFormat
import play.api.libs.ws.{WSClient, WSResponse}
import models.{APIError, User}
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


    def getAll(url: String)(ec: ExecutionContext): Future[Either[APIError, Seq[User]]] = {
      val request = ws.url(url).get()
      request.map{
        response =>
          response.json.as[Seq[User]].map{
            user =>
          }
      }
    }
}

