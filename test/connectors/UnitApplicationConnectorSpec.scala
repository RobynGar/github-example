package connectors

import play.api.libs.json._
import play.api.mvc._
import play.api.routing.sird._
import play.core.server.Server
import play.api.test._
import baseSpec.BaseSpecWithApplication
import models.{APIError, User}

import scala.concurrent.Await
import scala.concurrent.duration._


class UnitApplicationConnectorSpec extends BaseSpecWithApplication {

  private val user: User = User("octocat", "2011-01-25T18:44:36Z", Some("San Francisco"), 6536, 9)

  "ApplicationConnector .get()" should {

    "get a user from the github api" in {
      Server.withRouterFromComponents() { components =>
        import Results._
        import components.{defaultActionBuilder => Action}
        {
          case GET(p"/github/users/api/octocat") =>
            Action {
              Ok(Json.obj("login" -> "octocat", "created_at" -> "2011-01-25T18:44:36Z", "location" -> Some("San Francisco"), "followers" -> 6536, "following" -> 9))
            }
        }
      } { implicit port =>
        WsTestClient.withClient { client =>
          val result = Await.result(new ApplicationConnector(client).get[User]("https://api.github.com/users/octocat"), 10.seconds)
          result shouldBe Right(user)
        }
      }
    }

    "return error when trying to get a user from the github api that does not exist" in {
      Server.withRouterFromComponents() { components =>
        import Results._
        import components.{defaultActionBuilder => Action}
        {
          case GET(p"/github/users/api/meeptot") =>
            Action {
              NotFound(Json.obj("message" -> "Not Found", "documentation_url" -> "https://docs.github.com/rest/reference/users#get-a-user"))
            }
        }
      } { implicit port =>
        WsTestClient.withClient { client =>
          val result = Await.result(new ApplicationConnector(client).get[User]("https://api.github.com/users/meeptot"), 10.seconds)
          result shouldBe Left(APIError.BadAPIResponse(400, "could not find user"))
        }
      }
    }

  }


}
