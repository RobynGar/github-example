package connectors


import com.github.tomakehurst.wiremock.stubbing.StubMapping
import helpers.{IntegrationBaseSpec, WireMockHelper}
import models.User
import org.scalamock.scalatest.MockFactory
import play.api.http.Status._
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import stubs.ApplicationConnectorStub
import stubs.ApplicationConnectorStub._

class WireMockAppConnectorSpec extends IntegrationBaseSpec {


  private trait Test {
    def setupStubs(): StubMapping

    val testAppConnector: ApplicationConnector = app.injector.instanceOf[ApplicationConnector]
  }

  private def prefixUrl(url: String): String = {
    s"http://localhost:11111$url"
  }

  private val user: User = User("octocat", "2011-01-25T18:44:36Z", Some("San Francisco"), 6536, 9)

  private val repoNameList: List[String] = List("octocat", "boysenberry-repo-1", "git-consortium", "MIT License", "hello-worId", "Hello-World", "linguist", "MIT License", "octocat.github.io", "Spoon-Knife", "test-repo1")

  "ApplicationConnector .get()" should {

    "get a user from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = OK, fakeUser = responseUser, path = "/user/test/octocat"
      )

      val expected = Right(user)

      setupStubs()
      private val result = await(testAppConnector.get[User](prefixUrl("/user/test/octocat")))
      println(result match {
        case Right(a) => a
        case Left(value) => value
      }
      )
      result shouldBe expected
    }


  }


}
