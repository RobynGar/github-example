package controllers

import baseSpec.BaseSpecWithApplication
import models._
import org.apache.http.impl.client.FutureRequestExecutionMetrics
import org.scalamock.scalatest.MockFactory
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContent, AnyContentAsEmpty, Request, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, contentType, defaultAwaitTimeout, status}
import services.ApplicationService

import scala.concurrent.{ExecutionContext, Future}

class UnitApplicationControllerSpec extends BaseSpecWithApplication with MockFactory{

  val mockService: ApplicationService = mock[ApplicationService]

  val UnitTestApplicationController = new ApplicationController(
    component, mockService
  )

  private val user: User = User(
    "test",
    "now",
    Some("test location"),
    2,
    0
  )

  private val updatedUser: User = User(
    "test",
    "now",
    Some("test location"),
    2,
    20
  )

  private val apiUser: User = User(
    "Bla",
    "2016-08-18T08:36:43Z",
    None,
    2,
    0
  )
  private val returnedFile: ReturnCreatedFile = ReturnCreatedFile(
    Content("testName", "folder/testFile", "file", "shalala")
  )

  private val emptyUserSequence: Seq[User] = Seq()
  private val userSequence: Seq[User] = Seq(user)

  private val testRepo: Repository = Repository(
    "RobynGar", "play-template", false, "https://", "https://"
  )

  private val testFoldersFiles: FFitems = FFitems(
    "test folder", "dir", "testFolder", "https://", "shalala"
  )

  private val testFile: File = File(
    "test file", "shalala", "file", "testFolder/testFile", "https://", "https://", "content", "base64 content"
  )
  private val testCreateFile: CreateFile = CreateFile(
    "add test file", "content of created file"
  )
  private val updateFile: CreateFile = CreateFile(
    "test update method",
    "new content"
  )

  "ApplicationController unit test .index" should {

    "return when there are no users" in {

          val request: FakeRequest[AnyContent] = buildGet("/github/users")
          (() => mockService.index()).expects().returning(Future(Right(emptyUserSequence))).once()
          val indexResult: Future[Result] = UnitTestApplicationController.index()(request)

          status(indexResult) shouldBe Status.OK
          contentAsJson(indexResult).as[Seq[User]] shouldBe emptyUserSequence


    }

    "return all users in the database" in {

      val readAllRequest: FakeRequest[AnyContent] = buildGet("/github/users")
      (() => mockService.index()).expects().returning(Future(Right(userSequence))).once()
      val readAllResult: Future[Result] = UnitTestApplicationController.index()(readAllRequest)

      status(readAllResult) shouldBe Status.OK
      contentAsJson(readAllResult).as[Seq[User]] shouldBe userSequence

    }


  }

  "ApplicationController unit test .create()" should {

    "create a user in the database" in {


      val request: FakeRequest[JsValue] = buildPost("/github/users/create").withBody[JsValue](Json.toJson(user))
      (mockService.create(_: Request[JsValue])).expects(request).returning(Future(Right(user))).once()
      val createdResult: Future[Result] = UnitTestApplicationController.create()(request)

      status(createdResult) shouldBe Status.CREATED
      contentAsJson(createdResult).as[User] shouldBe user


    }

    "try to create a user in the database with wrong format" in {

      val request = buildPost("/github/users").withBody[JsValue](Json.obj())
      (mockService.create(_: Request[JsValue])).expects(request).returning(Future(Left(APIError.BadAPIResponse(400, "could not add user")))).once()
      val createdResult = UnitTestApplicationController.create()(request)
      status(createdResult) shouldBe Status.BAD_REQUEST
      contentAsJson(createdResult) shouldBe Json.toJson("could not add user")

    }

  }

  "ApplicationController unit test .read()" should {

    "find a user in the database by login" in {

      val readRequest: FakeRequest[AnyContent] = buildGet("/github/users/test")
      (mockService.read(_: String)).expects(*).returning(Future(Right(user))).once()
      val readResult: Future[Result] = UnitTestApplicationController.read("test")(readRequest)

      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[User] shouldBe user

    }

    "cannot find a user in the database as login does not exist" in {
      beforeEach()
      val readRequest: FakeRequest[AnyContent] = buildGet("/github/users/5")
      (mockService.read(_: String)).expects(*).returning(Future(Left(APIError.BadAPIResponse(400, "could not find any users")))).once()
      val readResult: Future[Result] = UnitTestApplicationController.read("5")(readRequest)

      status(readResult) shouldBe Status.BAD_REQUEST
      contentAsJson(readResult) shouldBe Json.toJson("could not find any users")
      afterEach()
    }

  }

  "ApplicationController unit test .update()" should {

    "find a user in the database by login and update all fields" in {

      val updateRequest: FakeRequest[JsValue] = buildPut("/github/users/test").withBody[JsValue](Json.toJson(updatedUser))
      (mockService.update(_: String, _: Request[JsValue])).expects(*, *).returning(Future(Right(updatedUser))).once()
      val updateResult: Future[Result] = UnitTestApplicationController.update("test")(updateRequest)


      status(updateResult) shouldBe Status.ACCEPTED
      contentAsJson(updateResult).as[User] shouldBe updatedUser

    }

    "try update a user by an login that does not exist and in a non-conforming format" in {

      val updateRequest = buildPut("/github/users/meep").withBody[JsValue](Json.obj())
      (mockService.update(_: String, _: Request[JsValue])).expects(*, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not update user information")))).once()
      val updateResult = UnitTestApplicationController.update("meep")(updateRequest)

      status(updateResult) shouldBe Status.BAD_REQUEST
      contentAsJson(updateResult) shouldBe Json.toJson("could not update user information")

    }

  }

  "ApplicationController unit test .delete()" should {

    "find a user in the database by login and delete" in {


      val deleteRequest: FakeRequest[AnyContent] = buildDelete("/github/users/test")
      (mockService.delete(_: String)).expects(*).returning(Future(Right("deleted"))).once()
      val deleteResult: Future[Result] = UnitTestApplicationController.delete("test")(deleteRequest)

      status(deleteResult) shouldBe Status.ACCEPTED

    }

    "cannot find a user in the database as login does not exist do not delete" in {

      val deleteRequest: FakeRequest[AnyContent] = buildDelete("/github/users/meep")
      (mockService.delete(_: String)).expects(*).returning(Future(Left(APIError.BadAPIResponse(400, "could not delete user")))).once()
      val deleteResult: Future[Result] = UnitTestApplicationController.delete("meep")(deleteRequest)

      status(deleteResult) shouldBe Status.BAD_REQUEST
      contentAsJson(deleteResult) shouldBe Json.toJson("could not delete user")

    }

  }

  "ApplicationController .readFromAPI()" should {

      "use username/login to find user from api and return that user" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/api/bla")
        (mockService.getUser(_: String)(_: ExecutionContext))
          .expects("bla", executionContext)
          .returning(Future(Right(apiUser)))
          .once()

        val apiResult = UnitTestApplicationController.readFromAPI("bla")(apiRequest)
        status(apiResult) shouldBe Status.OK
        contentAsJson(apiResult) shouldBe Json.toJson(apiUser)

        }



      "unknown username/login used to find user from api cannot return that user" in {
        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/api/meeptot")
        (mockService.getUser(_: String)(_: ExecutionContext))
          .expects("meeptot", executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find user"))))
          .once()
        val apiResult = UnitTestApplicationController.readFromAPI("meeptot")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find user")
      }
  }

    "ApplicationController .addFromAPI()" should {

      "add user to mongodb after using username/login to find user from api and return that user" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/add/bla")
        (mockService.addApiUser(_: String))
          .expects("bla")
          .returning(Future(Right(apiUser)))
          .once()
        val apiResult = UnitTestApplicationController.addFromAPI("bla")(apiRequest)

        status(apiResult) shouldBe Status.CREATED
        contentAsJson(apiResult) shouldBe Json.toJson(apiUser)

      }

      "unknown username/login used to find user from api cannot return that user" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/add/meeptot")
        (mockService.addApiUser(_: String))
          .expects("meeptot")
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not add user"))))
          .once()
        val apiResult = UnitTestApplicationController.addFromAPI("meeptot")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not add user")
      }
    }


    "ApplicationController .showUser()" should {

      "use username/login to find user from api and return that user" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/userspage/bla")
        (mockService.getUser(_: String)(_: ExecutionContext))
          .expects("bla", executionContext)
          .returning(Future(Right(apiUser)))
          .once()
        val apiResult = UnitTestApplicationController.showUser("bla")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentType(apiResult) shouldBe Some("text/html")

      }

      "use incorrect username/login return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/userspage/meeptot")
        (mockService.getUser(_: String)(_: ExecutionContext))
          .expects("meeptot", executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find user"))))
          .once()
        val apiResult = UnitTestApplicationController.showUser("meeptot")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find user")

      }
    }

    "ApplicationController .usersRepos()" should {

      "login to find user's repositories and return their names in list " in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/bla/repos")
        (mockService.getUsersRepo(_: String)(_: ExecutionContext))
          .expects("bla", executionContext)
          .returning(Future(Right(List("",""))))
          .once()
        val apiResult = UnitTestApplicationController.usersRepos("bla")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentType(apiResult) shouldBe Some("text/html")

      }

      "use incorrect login return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos")
        (mockService.getUsersRepo(_: String)(_: ExecutionContext))
          .expects(*, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))))
          .once()
        val apiResult = UnitTestApplicationController.usersRepos("meeptot")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories associated with that user")

      }

      "use login for user with no repositories, return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meep/repos")
        (mockService.getUsersRepo(_: String)(_: ExecutionContext))
          .expects(*, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))))
          .once()
        val apiResult = UnitTestApplicationController.usersRepos("meep")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories associated with that user")

      }
    }

    "ApplicationController() .usersRepoInfo()" should {

      "login to find user's repositories under the name specified and return info about that repo " in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/robyngar/play-template")
        (mockService.getUsersRepoInfo(_: String, _: String)(_: ExecutionContext))
          .expects(*, *, executionContext)
          .returning(Future(Right(testRepo)))
          .once()
        val apiResult = UnitTestApplicationController.usersRepoInfo("RobynGar", "play-template")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentType(apiResult) shouldBe Some("text/html")
      }

      "use incorrect login return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/test")
        (mockService.getUsersRepoInfo(_: String, _: String)(_: ExecutionContext))
          .expects(*, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories information"))))
          .once()
        val apiResult = UnitTestApplicationController.usersRepoInfo("meeptot", "test")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories information")

      }

      "use login for user with no repositories, return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meep/test")
        (mockService.getUsersRepoInfo(_: String, _: String)(_: ExecutionContext))
          .expects(*, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories information"))))
          .once()
        val apiResult = UnitTestApplicationController.usersRepoInfo("meep", "test")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories information")

      }
    }

    "ApplicationController() .repoContent()" should {

      "login to find user's repositories under the name specified and return info about that repo " in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/robyngar/repos/testcontent")
        (mockService.getRepoContent(_: String, _: String)(_: ExecutionContext))
          .expects(*, *, executionContext)
          .returning(Future(Right(Seq(testFoldersFiles))))
          .once()
        val apiResult = UnitTestApplicationController.repoContent("robyngar", "testcontent")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentType(apiResult) shouldBe Some("text/html")

      }

      "use incorrect login return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos/test")
        (mockService.getRepoContent(_: String, _: String)(_: ExecutionContext))
          .expects(*, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repository files"))))
          .once()
        val apiResult = UnitTestApplicationController.repoContent("meeptot", "test")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find any repository files")

      }

    }


    "ApplicationController() .dirContent()" should {

      "login to find user's repositories under the name specified and return folders and files form that repo " in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/bla/repos/android-runner/MonkeyPlayer")
        (mockService.getDirContent(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Right(Seq(testFoldersFiles))))
          .once()
        val apiResult = UnitTestApplicationController.dirContent("MonkeyPlayer", "bla", "android-runner")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentType(apiResult) shouldBe Some("text/html")

      }

      "use incorrect login return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos/test/testFolder")
        (mockService.getDirContent(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repository files"))))
          .once()
        val apiResult = UnitTestApplicationController.dirContent("testFolder", "meeptot", "test")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find any repository files")

      }

   }

    "ApplicationController() .fileContent()" should {

      "login to find user's repositories under the name specified and get the contents of a file" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/testUser/repos/testRepo/file/testDir/README.md")
        (mockService.getFileContent(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Right(testFile)))
          .once()
        val apiResult = UnitTestApplicationController.fileContent("testDir/README.md", "testUser", "testDir")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentType(apiResult) shouldBe Some("text/html")

      }

      "use incorrect login return api error" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos/test/file/testFolder/testFile")
        (mockService.getFileContent(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any file contents"))))
          .once()
        val apiResult = UnitTestApplicationController.fileContent("testFolder/testFile", "meeptot", "test")(apiRequest)

        status(apiResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiResult) shouldBe Json.toJson("could not find any file contents")

      }

    }

    "ApplicationController() .createFile()" should {

      "login to find user's repositories under the name specified and create file" in {

        val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/create/newFile3.txt").withBody[JsValue](Json.toJson(testCreateFile))
        (mockService.createFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext))
          .expects(*, *, *, *,  executionContext)
          .returning(Future(Right(returnedFile)))
          .once()
        val apiCreatedResult = UnitTestApplicationController.createFile("RobynGar", "git_practice", "newFile3.txt")(apiCreatedRequest)

        status(apiCreatedResult) shouldBe Status.OK

      }


      "correct login but repository does not exist" in {
        val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/made-up-repo/file/create/newFile.txt").withBody[JsValue](Json.toJson(testCreateFile))
        (mockService.createFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext))
          .expects(*, *, *, *,  executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not create file"))))
          .once()
        val apiCreatedResult = UnitTestApplicationController.createFile("RobynGar", "made-up-repo", "newFile.txt")(apiCreatedRequest)

        status(apiCreatedResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiCreatedResult) shouldBe Json.toJson("could not create file")
      }

    }

    "ApplicationController() .updateFile()" should {

      "login to find user's repositories under the name specified and update file" in {

        val apiUpdateRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/update/newFile.txt").withBody[JsValue](Json.toJson(updateFile))
        (mockService.updateFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext))
          .expects(*, *, *, *,  executionContext)
          .returning(Future(Right(returnedFile)))
          .once()
        val apiUpdateResult = UnitTestApplicationController.updateFile("RobynGar", "git_practice", "newFile.txt")(apiUpdateRequest)

        status(apiUpdateResult) shouldBe Status.OK
        contentAsJson(apiUpdateResult) shouldBe Json.toJson(returnedFile)

      }

      "correct login and repository but file does not exist" in {
        val apiUpdatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/update/nonExistent.txt").withBody[JsValue](Json.toJson(updateFile))
         (mockService.updateFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext))
           .expects(*, *, *, *,  executionContext)
           .returning(Future(Left(APIError.BadAPIResponse(400, "could not update file"))))
           .once()
        val apiUpdatedResult = UnitTestApplicationController.updateFile("RobynGar", "git_practice", "nonExistent.txt")(apiUpdatedRequest)

        status(apiUpdatedResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiUpdatedResult) shouldBe Json.toJson("could not update file")
      }
    }

    "ApplicationController() .deleteFile()" should {

      "login to find user's repositories under the name specified and delete file" in {

        val apiDeleteRequest: FakeRequest[JsValue] = buildDelete("/github/users/RobynGar/repos/git_practice/file/delete/newFile.txt").withBody[JsValue](Json.toJson("delete file"))
        (mockService.deleteFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext))
          .expects(*, *, *, *, executionContext)
          .returning(Future(Right("DELETED")))
          .once()
        val apiDeleteResult = UnitTestApplicationController.deleteFile("RobynGar", "git_practice", "newFile.txt")(apiDeleteRequest)

        status(apiDeleteResult) shouldBe Status.ACCEPTED
      }

      "correct login and repository but file does not exist" in {
        val apiDeleteRequest: FakeRequest[JsValue] = buildDelete("/github/users/RobynGar/repos/git_practice/file/delete/nonExistent.txt").withBody[JsValue](Json.toJson("deleteFile"))
        (mockService.deleteFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext))
          .expects(*, *, *, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(404, "could not delete file"))))
          .once()
        val apiDeleteResult = UnitTestApplicationController.deleteFile("RobynGar", "git_practice", "nonExistent.txt")(apiDeleteRequest)

        status(apiDeleteResult) shouldBe Status.NOT_FOUND
        contentAsJson(apiDeleteResult) shouldBe Json.toJson("could not delete file")
      }
    }

    "ApplicationController() .repoReadMe()" should {

      "login to find user's repositories under the name specified and get readMe" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/play-template/get/readme")
        (mockService.repoReadMe(_: String, _: String)(_: ExecutionContext))
          .expects(*, *, executionContext)
          .returning(Future(Right(testFile)))
          .once()
        val apiResult = UnitTestApplicationController.repoReadMe("RobynGar", "play-template")(apiRequest)

        status(apiResult) shouldBe Status.OK
      }

      "correct login and repository but no readme exists" in {
        val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/git_practice/get/readme")
        (mockService.repoReadMe(_: String, _: String)(_: ExecutionContext))
          .expects(*, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find readme for this repository"))))
          .once()
        val apiReadMeResult = UnitTestApplicationController.repoReadMe("RobynGar", "git_practice")(apiReadMeRequest)

        status(apiReadMeResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiReadMeResult) shouldBe Json.toJson("could not find readme for this repository")
      }
    }

    "ApplicationController() .dirReadMe()" should {

      "login to find user's repositories and get readMe from specified directory" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/play-template/readme/app")
        (mockService.dirReadMe(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Right(testFile)))
          .once()
        val apiResult = UnitTestApplicationController.dirReadMe("RobynGar", "play-template", "app")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentType(apiResult) shouldBe Some("text/html")
      }

       "correct login and repository but no readme exists in that directory" in {
         val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/git_practice/get/readme/project")
         (mockService.dirReadMe(_: String, _: String, _: String)(_: ExecutionContext))
           .expects(*, *, *, executionContext)
           .returning(Future(Left(APIError.BadAPIResponse(404, "resource not found"))))
           .once()
         val apiReadMeResult = UnitTestApplicationController.dirReadMe("RobynGar", "git_practice", "project")(apiReadMeRequest)

         status(apiReadMeResult) shouldBe Status.NOT_FOUND
         contentAsJson(apiReadMeResult) shouldBe Json.toJson("resource not found")

       }
    }

    "ApplicationController() .downloadTar()" should {

      "download the tar of the repository specified form the branch specified" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/git_practice/download/tarball/main")
        (mockService.downloadTar(_: String, _: String, _: Option[String])(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Right(302)))
          .once()
        val apiResult = UnitTestApplicationController.downloadTar("RobynGar", "git_practice", Some("main"))(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentAsJson(apiResult) shouldBe Json.toJson("Downloaded")
      }

      "correct login but no repository exists" in {
        val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/no-such-repo/download/tarball")
        (mockService.downloadTar(_: String, _: String, _: Option[String])(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not download tar"))))
          .once()
        val apiReadMeResult = UnitTestApplicationController.downloadTar("RobynGar", "no-such-repo", None)(apiReadMeRequest)

        status(apiReadMeResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiReadMeResult) shouldBe Json.toJson("could not download tar")
      }
    }

    "ApplicationController() .downloadZip()" should {

      "download the tar of the repository specified form the branch specified" in {

        val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/git_practice/download/zipball")
        (mockService.downloadZip(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Right(302)))
          .once()
        val apiResult = UnitTestApplicationController.downloadZip("RobynGar", "git_practice", "main")(apiRequest)

        status(apiResult) shouldBe Status.OK
        contentAsJson(apiResult) shouldBe Json.toJson("Downloaded")

      }

      "correct login but no repository exists" in {
        val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/no-such-repo/download/zipball")
        (mockService.downloadZip(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not download zip"))))
          .once()
        val apiReadMeResult = UnitTestApplicationController.downloadZip("RobynGar", "no-such-repo", "main")(apiReadMeRequest)

        status(apiReadMeResult) shouldBe Status.BAD_REQUEST
        contentAsJson(apiReadMeResult) shouldBe Json.toJson("could not download zip")
      }

    }
  
}
