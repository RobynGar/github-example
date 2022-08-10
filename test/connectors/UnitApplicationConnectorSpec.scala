package connectors

import play.api.libs.json._
import play.api.mvc._
import play.api.routing.sird._
import play.core.server.Server
import play.api.test._
import baseSpec.BaseSpecWithApplication
import models.{APIError, User}
import play.api.BuiltInComponentsFromContext
import play.api.routing.Router
import play.filters.HttpFiltersComponents

import scala.concurrent.Await
import scala.concurrent.duration._


class UnitApplicationConnectorSpec extends BaseSpecWithApplication {

  private val user: User = User("octocat", "2011-01-25T18:44:36Z", Some("San Francisco"), 6536, 9)

  private val repoNameList: List[String] = List("octocat", "boysenberry-repo-1", "git-consortium", "MIT License", "hello-worId", "Hello-World", "linguist", "MIT License", "octocat.github.io","Spoon-Knife", "test-repo1")

  "ApplicationConnector .get()" should {

    "get a user from the github api" in {
      Server.withRouterFromComponents() { components =>
        import Results._
        import components.{defaultActionBuilder => Action}
        {
          case GET(p"/users/octocat") =>
            Action {
              Ok(Json.obj(
                "login" -> "octocat",
                "created_at" -> "2011-01-25T18:44:36Z",
                "location" -> Some("San Francisco"),
                "followers" -> 6536,
                "following" -> 9))
            }
        }
      } { implicit port =>
        WsTestClient.withClient { client =>
          val result = Await.result(new ApplicationConnector(client).get[User]("/users/octocat"), 10.seconds)
          result shouldBe Right(user)
        }
      }
    }


    "return error when trying to get a user from the github api that does not exist" in {
      Server.withRouterFromComponents() { components =>
        import Results._
        import components.{defaultActionBuilder => Action}
        {
          case GET(p"/users/meeptot") =>
            Action {
              NotFound(Json.obj("message" -> "Not Found", "documentation_url" -> "https://docs.github.com/rest/reference/users#get-a-user"))
            }
        }
      } { implicit port =>
        WsTestClient.withClient { client =>
          val result = Await.result(new ApplicationConnector(client).get[User]("/users/meeptot"), 10.seconds)
          result shouldBe Left(APIError.BadAPIResponse(400, "could not find user"))
        }
      }
    }

  }

  "ApplicationConnector .getUserRepo()" should {

    "get a user's repo name list from the github api" in {
      Server.withApplicationFromContext() { context =>
        new BuiltInComponentsFromContext(context) with HttpFiltersComponents {
          override def router: Router = Router.from {
            case GET(p"/users/octocat/repos") =>
              Action { req =>
                Results.Ok.sendResource("github/repositories.json")(executionContext, fileMimeTypes)
              }
          }
        }.application
      } { implicit port =>
        WsTestClient.withClient { client =>
          val result = Await.result(new ApplicationConnector(client).getUserRepo[User]("/users/octocat/repos"), 10.seconds)
          result shouldBe Right(repoNameList)
        }
      }
    }


    "return error when trying to get a user repo names from the github api when user does not exist" in {
      Server.withApplicationFromContext() { context =>
        new BuiltInComponentsFromContext(context) with HttpFiltersComponents {
          override def router: Router = Router.from {
            case GET(p"/users/octocat/repos") =>
              Action { req =>
                Results.Ok.sendResource("github/errorRepositories.json")(executionContext, fileMimeTypes)
              }
          }
        }.application
      }  { implicit port =>
        WsTestClient.withClient { client =>
          val result = Await.result(new ApplicationConnector(client).getUserRepo[User]("/users/meeptot/repos"), 10.seconds)
          result shouldBe Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))

        }
      }
    }

  }




}
