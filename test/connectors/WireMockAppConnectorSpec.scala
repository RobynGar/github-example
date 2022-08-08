package connectors


import com.github.tomakehurst.wiremock.stubbing.StubMapping
import helpers.{IntegrationBaseSpec, WireMockHelper}
import models.{APIError, FFitems, File, Repository, User}
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

  private val repoNameList: List[String] = List("octocat", "boysenberry-repo-1", "git-consortium", "hello-worId")

  private val seqOfFF: Seq[FFitems] = Seq(
    FFitems(
    "README.md","file", "README.md", "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/README.md?ref=master", "dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d"
  ),
    FFitems(
    "READTHIS.md", "file", "READTHIS.md", "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/READTHIS.md?ref=master", "e69de29bb2d1d6434b8b29ae775ad8c2e48c5391"
  )
  )
  private val file: File = File(
    "READTHIS.md", "fggtyhuyju", "file", "service/ApplicationService", "url", "testurl", "YmFzZTY0", "base64"
  )

  "ApplicationConnector .get()" should {

    "get a user from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = OK, response = responseUser, path = "/user/test/octocat"
      )

      val expected = Right(user)

      setupStubs()
      private val result = await(testAppConnector.get[User](prefixUrl("/user/test/octocat")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get user" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot"
      )

      val expected =  Left(APIError.BadAPIResponse(400, "could not find user"))

      setupStubs()
      private val result = await(testAppConnector.get[User](prefixUrl("/user/test/meeptot")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .getUserRepo()" should {

    "get a user repositories from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = OK, response = usersRepos, path = "/user/repos/test/octocat"
      )

      val expected = Right(repoNameList)

      setupStubs()
      private val result = await(testAppConnector.getUserRepo[Repository](prefixUrl("/user/repos/test/octocat")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get user repositories" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = NOT_FOUND, response = errorJson, path = "/user/repos/test/meeptot"
      )

      val expected =  Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))

      setupStubs()
      private val result = await(testAppConnector.getUserRepo[Repository](prefixUrl("/user/repos/test/meeptot")))

      result shouldBe expected
    }

  }


  "ApplicationConnector .getRepoContent()" should {

    "get a user repository content from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = OK, response = usersReposContent, path = "/user/test/octocat/repoName/content"
      )

      val expected = Right(seqOfFF)

      setupStubs()
      private val result = await(testAppConnector.getRepoContent[FFitems](prefixUrl("/user/test/octocat/repoName/content")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get user repository content" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repoName/content"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find any repository files"))

      setupStubs()
      private val result = await(testAppConnector.getRepoContent[FFitems](prefixUrl("/user/test/meeptot/repoName/content")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .getDirContent()" should {

    "get the content of a ser directory from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = OK, response = usersReposContent, path = "/user/test/octocat/repoName/dir/content"
      )

      val expected = Right(seqOfFF)

      setupStubs()
      private val result = await(testAppConnector.getDirContent[FFitems](prefixUrl("/user/test/octocat/repoName/dir/content")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get content of a user directory" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repoName/dir/content"
      )

      val expected =  Left(APIError.BadAPIResponse(400, "could not find any repository files"))

      setupStubs()
      private val result = await(testAppConnector.getDirContent[FFitems](prefixUrl("/user/test/meeptot/repoName/dir/content")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .getFileContent()" should {

    "get the content of a user directory from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = OK, response = usersRepoFile, path = "/user/test/octocat/repoName/file/content"
      )

      val expected = Right(file)

      setupStubs()
      private val result = await(testAppConnector.getFileContent[File](prefixUrl("/user/test/octocat/repoName/file/content")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get content of a user directory" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetUser(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repoName/file/content"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find any file contents"))

      setupStubs()
      private val result = await(testAppConnector.getFileContent[File](prefixUrl("/user/test/meeptot/repoName/file/content")))

      result shouldBe expected
    }

  }


}
