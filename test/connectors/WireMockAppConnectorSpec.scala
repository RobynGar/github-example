package connectors


import com.github.tomakehurst.wiremock.stubbing.StubMapping
import helpers.IntegrationBaseSpec
import models._
import play.api.http.Status._
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import stubs.ApplicationConnectorStub
import stubs.ApplicationConnectorStub._

class WireMockAppConnectorSpec extends IntegrationBaseSpec {


  private trait Test {
    def setupStubs(): StubMapping
    val testAppConnector: ApplicationConnector = app.injector.instanceOf[ApplicationConnector]
  }


  private val user: User = User("octocat", "2011-01-25T18:44:36Z", Some("San Francisco"), 6536, 9)

  private val repoNameList: List[String] = List("octocat", "boysenberry-repo-1", "git-consortium", "hello-worId")

  private val seqOfFF: Seq[FFitems] = Seq(
    FFitems(
      "README.md", "file", "README.md", "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/README.md?ref=master", "dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d"
    ),
    FFitems(
      "READTHIS.md", "file", "READTHIS.md", "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/READTHIS.md?ref=master", "e69de29bb2d1d6434b8b29ae775ad8c2e48c5391"
    )
  )
  private val file: File = File(
    "READTHIS.md", "fggtyhuyju", "file", "service/ApplicationService", "url", "testurl", "YmFzZTY0", "base64"
  )

  private val repo: Repository = Repository("octocat", "testRepo", false, "https://api.github.com/repos/octocat/testRepo", "https://github.com/octocat/testRepo")

  private val createdFile: ReturnCreatedFile = ReturnCreatedFile(Content("hello.txt", "notes/hello.txt", "file"))


  private def prefixUrl(url: String): String = {
    s"http://localhost:11111$url"
  }

  "ApplicationConnector .get()" should {

    "get a user from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = responseUser, path = "/user/test/octocat"
      )

      val expected = Right(user)

      setupStubs()
      private val result = await(testAppConnector.get[User](prefixUrl("/user/test/octocat")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get user" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find user"))

      setupStubs()
      private val result = await(testAppConnector.get[User](prefixUrl("/user/test/meeptot")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .getUserRepo()" should {

    "get a user repositories from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = usersRepos, path = "/user/repos/test/octocat"
      )

      val expected = Right(repoNameList)

      setupStubs()
      private val result = await(testAppConnector.getUserRepo[Repository](prefixUrl("/user/repos/test/octocat")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get user repositories" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/user/repos/test/meeptot"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))

      setupStubs()
      private val result = await(testAppConnector.getUserRepo[Repository](prefixUrl("/user/repos/test/meeptot")))

      result shouldBe expected
    }

  }


  "ApplicationConnector .getRepoContent()" should {

    "get a user repository content from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = usersReposContent, path = "/user/test/octocat/repoName/content"
      )

      val expected = Right(seqOfFF)

      setupStubs()
      private val result = await(testAppConnector.getRepoContent[FFitems](prefixUrl("/user/test/octocat/repoName/content")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get user repository content" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
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
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = usersReposContent, path = "/user/test/octocat/repoName/dir/content"
      )

      val expected = Right(seqOfFF)

      setupStubs()
      private val result = await(testAppConnector.getDirContent[FFitems](prefixUrl("/user/test/octocat/repoName/dir/content")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get content of a user directory" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repoName/dir/content"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find any repository files"))

      setupStubs()
      private val result = await(testAppConnector.getDirContent[FFitems](prefixUrl("/user/test/meeptot/repoName/dir/content")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .getFileContent()" should {

    "get the content of a user directory from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = usersRepoFile, path = "/user/test/octocat/repoName/file/content"
      )

      val expected = Right(file)

      setupStubs()
      private val result = await(testAppConnector.getFileContent[File](prefixUrl("/user/test/octocat/repoName/file/content")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get content of a user directory" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repoName/file/content"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find any file contents"))

      setupStubs()
      private val result = await(testAppConnector.getFileContent[File](prefixUrl("/user/test/meeptot/repoName/file/content")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .getUserRepoInfo()" should {

    "get information about a user repository from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = reposInfo, path = "/user/test/octocat/repo/info"
      )

      val expected = Right(repo)

      setupStubs()
      private val result = await(testAppConnector.getUserRepoInfo[Repository](prefixUrl("/user/test/octocat/repo/info")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get information of a user repository" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repo/info"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find any repositories information"))

      setupStubs()
      private val result = await(testAppConnector.getUserRepoInfo[Repository](prefixUrl("/user/test/meeptot/repo/info")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .createFile()" should {

    "create a file in a user repository in the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubPut(
        status = CREATED, response = gitCreateFile, path = "/user/test/octocat/create/file"
      )

      val expected = Right(createdFile)

      setupStubs()
      private val result = await(testAppConnector.createFile[ReturnCreatedFile](CreateFile("my commit message", "test file content"), prefixUrl("/user/test/octocat/create/file")))

      result shouldBe expected
    }

    "get a error from the github api when trying to create a file in a user's repository" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubPut(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/create/file"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not create file"))

      setupStubs()
      private val result = await(testAppConnector.createFile[ReturnCreatedFile](CreateFile("my commit message", "test file content"), prefixUrl("/user/test/meeptot/create/file")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .updateFile()" should {

    "update a file in a user repository in the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubPut(
        status = CREATED, response = gitCreateFile, path = "/user/test/octocat/update/file"
      )

      val expected = Right(createdFile)

      setupStubs()
      private val result = await(testAppConnector.updateFile[ReturnCreatedFile](CreateFile("my commit message", "test file content"), prefixUrl("/user/test/octocat/update/file"), "95b966ae1c166bd92f8ae7d1c313e738c731dfc3"))

      result shouldBe expected
    }

    "get a error from the github api when trying to update a file in a user's repository" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubPut(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/update/file"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not update file"))

      setupStubs()
      private val result = await(testAppConnector.updateFile[ReturnCreatedFile](CreateFile("my commit message", "test file content"), prefixUrl("/user/test/meeptot/update/file"), "95b966ae1c166bd92f8ae7d1c313e738c731dfc3"))

      result shouldBe expected
    }

  }

  "ApplicationConnector .deleteFile()" should {

    "delete a file in a user repository in the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubDelete(
        status = OK, response = gitDeletedFile, path = "/user/test/octocat/delete/file"
      )

      val expected = Right(DeletedReturn(None))

      setupStubs()
      private val result = await(testAppConnector.deleteFile[ReturnCreatedFile](prefixUrl("/user/test/octocat/delete/file"), RequestDelete("delete", "95b966ae1c166bd92f8ae7d1c313e738c731dfc3")))

      result shouldBe expected
    }

    "get a error from the github api when trying to delete a file in a user's repository" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubDelete(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/delete/file"
      )

      val expected = Left(APIError.BadAPIResponse(404, "could not delete file"))

      setupStubs()
      private val result = await(testAppConnector.deleteFile[ReturnCreatedFile](prefixUrl("/user/test/meeptot/delete/file"), RequestDelete("delete", "95b966ae1c166bd92f8ae7d1c313e738c731dfc3")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .repoReadMe()" should {

    "get repository readme from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = usersRepoFile, path = "/user/test/octocat/repo/readme"
      )

      val expected = Right(file)

      setupStubs()
      private val result = await(testAppConnector.repoReadMe[File](prefixUrl("/user/test/octocat/repo/readme")))

      result shouldBe expected
    }

    "get a error from the github api when trying to get readme of a repository that does not exist" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repo/readme"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not find readme for this repository"))

      setupStubs()
      private val result = await(testAppConnector.repoReadMe[File](prefixUrl("/user/test/meeptot/repo/readme")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .dirReadMe()" should {

    "get directory readme from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = usersRepoFile, path = "/user/test/octocat/repo/dir/readme"
      )

      val expected = Right(file)

      setupStubs()
      private val result = await(testAppConnector.dirReadMe[File](prefixUrl("/user/test/octocat/repo/dir/readme")))

      result shouldBe expected
    }

    "get a not-found error from the github api when trying to get readme of a directory that does not exist" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/user/test/meeptot/repo/dir/readme"
      )

      val expected = Left(APIError.BadAPIResponse(404, "resource not found"))

      setupStubs()
      private val result = await(testAppConnector.dirReadMe[File](prefixUrl("/user/test/meeptot/repo/dir/readme")))

      result shouldBe expected
    }

    "get a UNPROCESSABLE-entity error from the github api when trying to get readme of a directory that does not exist" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = UNPROCESSABLE_ENTITY, response = errorJson, path = "/user/test/meeptot/repo/dir/readme"
      )

      val expected = Left(APIError.BadAPIResponse(422, "validation failed"))

      setupStubs()
      private val result = await(testAppConnector.dirReadMe[File](prefixUrl("/user/test/meeptot/repo/dir/readme")))

      result shouldBe expected
    }

    "get a 200 http response but unable to validate response as a file" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = OK, response = usersRepos, path = "/user/test/meeptot/repo/dir/readme"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not validate as file"))

      setupStubs()
      private val result = await(testAppConnector.dirReadMe[File](prefixUrl("/user/test/meeptot/repo/dir/readme")))

      result shouldBe expected
    }

  }

  "ApplicationConnector .downloadTar()" should {

    "download tarball from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetNoBody(
        status = OK, path = "/test/octocat/repo/Tar"
      )

      val expected = Right(302)

      setupStubs()
      private val result = await(testAppConnector.downloadTar[File](prefixUrl("/test/octocat/repo/Tar")))

      result shouldBe expected
    }

    "get a error when trying to download tarball from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/test/meeptot/repo/Tar"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not download tar"))

      setupStubs()
      private val result = await(testAppConnector.downloadTar[File](prefixUrl("/test/meeptot/repo/Tar")))

      result shouldBe expected
    }

  }


  "ApplicationConnector .downloadZip()" should {

    "download zip from the github api" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGetNoBody(
        status = OK, path = "/test/octocat/repo/zip"
      )

      val expected = Right(302)

      setupStubs()
      private val result = await(testAppConnector.downloadZip[File](prefixUrl("/test/octocat/repo/zip")))

      result shouldBe expected
    }

    "get a error from the github api when trying to download zip" in new Test {
      override def setupStubs(): StubMapping = ApplicationConnectorStub.stubGet(
        status = NOT_FOUND, response = errorJson, path = "/test/meeptot/repo/zip"
      )

      val expected = Left(APIError.BadAPIResponse(400, "could not download zip"))

      setupStubs()
      private val result = await(testAppConnector.downloadZip[File](prefixUrl("/test/meeptot/repo/zip")))

      result shouldBe expected
    }

  }

}
