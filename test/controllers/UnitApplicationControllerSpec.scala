package controllers

import baseSpec.BaseSpecWithApplication
import models.{APIError, DeletedReturn, FFitems, File, Repository, User}
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContent, Request, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, contentAsString, contentType, defaultAwaitTimeout, status}
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

  private val emptyUserSequence: Seq[User] = Seq()
  private val userSequence: Seq[User] = Seq(user)

  private val repo: Repository = Repository(
    "test",
    "testRepo",
    false,
    "testURL",
    "testhtmlURL"
  )
  private val foldersFiles: FFitems = FFitems(
    "test folder", "dir", "app/service", "testurl", "shalala"
  )
  private val file: File = File(
    "test file", "fggtyhuyju", "file", "service/ApplicationService", "http://", "testurl", "base64", "decoded url"
  )

  private val repoNameList: List[String] = List("test", "repo1", "repo2", "test-repo1")


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

  "ApplicationController unit test .readFromAPI()" should {

    "find a user from github api and return" in {

      val request = buildGet("/github/users/api/test")
      (mockService.getUser(_: String)(_: ExecutionContext))
        .expects(*, executionContext)
        .returning(Future(Right(user)))
        .once()
      val result = UnitTestApplicationController.readFromAPI("test")(request)

      status(result) shouldBe Status.OK
      contentAsJson(result) shouldBe Json.toJson(user)

    }
    "unable to find a user from github api" in {

      val request = buildGet("/github/users/api/noUser")
      (mockService.getUser(_: String)(_: ExecutionContext))
        .expects(*, executionContext)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not find user"))))
        .once()
      val result = UnitTestApplicationController.readFromAPI("test")(request)

      status(result) shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe Json.toJson("could not find user")

    }

  }

  "ApplicationController unit test .addFromAPI()" should {

    "find a user from github api and add to database" in {

      val request = buildGet("/github/users/add/test")

      (mockService.addApiUser(_: String))
        .expects(*)
        .returning(Future(Right(user)))
        .once()

      val result = UnitTestApplicationController.addFromAPI("test")(request)

      status(result) shouldBe Status.CREATED
      contentAsJson(result) shouldBe Json.toJson(user)

    }
    "unable to find a user from github api and  cannot add to database" in {

      val request = buildGet("/github/users/add/test")

      (mockService.addApiUser(_: String))
        .expects(*)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not add user"))))
        .once()

      val result = UnitTestApplicationController.addFromAPI("test")(request)

      status(result) shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe Json.toJson("could not add user")

    }
  }

  "ApplicationController unit test .showUser()" should {

    "find a user from github api and display on html page" in {

      val request = buildGet("/github/userspage/test")

      (mockService.getUser(_: String)(_: ExecutionContext))
        .expects(*, executionContext)
        .returning(Future(Right(user)))
        .once()

      val result = UnitTestApplicationController.showUser("test")(request)

      status(result) shouldBe Status.OK
      contentType(result) shouldBe Some("text/html")
      contentAsString(result) must include ("test")

    }

    "unable to find a user from github api return error" in {

      val request = buildGet("/github/userspage/noUser")

      (mockService.getUser(_: String)(_: ExecutionContext))
        .expects(*, executionContext)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not find user"))))
        .once()

      val result = UnitTestApplicationController.showUser("noUser")(request)

      status(result) shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe Json.toJson("could not find user")

    }
  }


  "ApplicationController unit test .usersRepos()" should {

    "find a user's repos from github api and display list on html page" in {

      val request = buildGet("/github/users/test/repos")

      (mockService.getUsersRepo(_: String)(_: ExecutionContext))
        .expects(*, executionContext)
        .returning(Future(Right(repoNameList)))
        .once()

      val result = UnitTestApplicationController.usersRepos("test")(request)

      status(result) shouldBe Status.OK
      contentType(result) shouldBe Some("text/html")
      contentAsString(result) must include ("repo1")

    }

    "unable to find a user's repos from github api return error" in {

      val request = buildGet("/github/users/noUser/repos")

      (mockService.getUsersRepo(_: String)(_: ExecutionContext))
        .expects(*, executionContext)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))))
        .once()

      val result = UnitTestApplicationController.usersRepos("noUser")(request)

      status(result) shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe Json.toJson("could not find any repositories associated with that user")

    }
  }

  "ApplicationController unit test .usersRepoInfo()" should {

    "find a user's repos from github api and display repos information on html page" in {

      val request = buildGet("/github/users/test/testRepo")

      (mockService.getUsersRepoInfo(_: String, _: String)(_: ExecutionContext))
        .expects(*, *, executionContext)
        .returning(Future(Right(repo)))
        .once()

      val result = UnitTestApplicationController.usersRepoInfo("test", "testRepo")(request)

      status(result) shouldBe Status.OK
      contentType(result) shouldBe Some("text/html")
      contentAsString(result) must include ("testRepo")

    }

    "unable to find repository and display information from github api return error" in {

      val request = buildGet("/github/users/test/noRepo")

      (mockService.getUsersRepoInfo(_: String, _: String)(_: ExecutionContext))
        .expects(*, *, executionContext)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories information"))))
        .once()

      val result = UnitTestApplicationController.usersRepoInfo("test", "noRepo")(request)

      status(result) shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe Json.toJson("could not find any repositories information")

    }
  }

  "ApplicationController unit test .repoContent()" should {

    "find a user's repos and display content from github api on html page" in {

      val request = buildGet("/github/users/test/repos/testRepo")

      (mockService.getRepoContent(_: String, _: String)(_: ExecutionContext))
        .expects(*, *, executionContext)
        .returning(Future(Right(Seq(foldersFiles))))
        .once()

      val result = UnitTestApplicationController.repoContent("test", "testRepo")(request)

      status(result) shouldBe Status.OK
      contentType(result) shouldBe Some("text/html")
      contentAsString(result) must include ("Folders")

    }

    "unable to find repository and display content from github api return error" in {

      val request = buildGet("/github/users/test/repos/noRepo")

      (mockService.getRepoContent(_: String, _: String)(_: ExecutionContext))
        .expects(*, *, executionContext)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repository files"))))
        .once()

      val result = UnitTestApplicationController.repoContent("test", "testRepo")(request)

      status(result) shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe Json.toJson("could not find any repository files")

    }
  }

  "ApplicationController unit test .dirContent()" should {

    "find a user's directory and display content from github api on html page" in {

      val request = buildGet("/github/users/test/repos/testRepo/testDir")

      (mockService.getDirContent(_: String, _: String, _: String)(_: ExecutionContext))
        .expects(*, *, *, executionContext)
        .returning(Future(Right(Seq(foldersFiles))))
        .once()

      val result = UnitTestApplicationController.dirContent("testDir", "test", "testRepo")(request)

      status(result) shouldBe Status.OK
      contentType(result) shouldBe Some("text/html")
      contentAsString(result) must include ("Folders")

    }

    "unable to find directory and display content from github api return error" in {

        val request = buildGet("/github/users/test/repos/testRepo/noDir")

        (mockService.getDirContent(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, executionContext)
          .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repository files"))))
          .once()

        val result = UnitTestApplicationController.dirContent("noDir", "test", "testRepo")(request)


      status(result) shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe Json.toJson("could not find any repository files")

    }
  }

  "ApplicationController unit test .fileContent()" should {

    "find a user's directory and display content from github api on html page" in {

      val request = buildGet("/github/users/test/repos/testRepo/testDir")

      (mockService.getFileContent(_: String, _: String, _: String)(_: ExecutionContext))
        .expects(*, *, *, executionContext)
        .returning(Future(Right(file)))
        .once()

      val result = UnitTestApplicationController.fileContent("testFile", "test", "testRepo")(request)

      status(result) shouldBe Status.OK
      contentType(result) shouldBe Some("text/html")
      contentAsString(result) must include ("test file")

    }

//    "unable to find directory and display content from github api return error" in {
//
//      val request = buildGet("/github/users/test/repos/testRepo/noDir")
//
//      (mockService.getDirContent(_: String, _: String, _: String)(_: ExecutionContext))
//        .expects(*, *, *, executionContext)
//        .returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repository files"))))
//        .once()
//
//      val result = UnitTestApplicationController.dirContent("noDir", "test", "testRepo")(request)
//
//
//      status(result) shouldBe Status.BAD_REQUEST
//      contentAsJson(result) shouldBe Json.toJson("could not find any repository files")
//
//    }
  }

  "ApplicationController unit test .deleteFile()" should {

    "find a file in a repository and delete it" in {


      val deleteRequest = buildDelete("/github/users/test/repos/testRepo/file/delete/testFile").withBody[JsValue](Json.toJson("delete message"))
      (mockService.deleteFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext)).expects(*, *, *, deleteRequest, executionContext).returning(Future(Right(DeletedReturn(None)))).once()
      val deleteResult = UnitTestApplicationController.deleteFile("test", "testRepo", "testfile")(deleteRequest)

      status(deleteResult) shouldBe Status.ACCEPTED

    }

    "cannot find a file to delete it in the repository as repository does not exist" in {

      val deleteRequest = buildDelete("/github/users/test/repos/testRepo/file/delete/noFile").withBody[JsValue](Json.toJson("delete message"))
      (mockService.deleteFile(_: String, _: String, _: String, _: Request[JsValue])(_: ExecutionContext)).expects(*, *, *, deleteRequest, executionContext).returning(Future(Left(APIError.BadAPIResponse(404, "could not delete file")))).once()
      val deleteResult = UnitTestApplicationController.deleteFile("test", "testRepo", "noFile")(deleteRequest)

      status(deleteResult) shouldBe Status.NOT_FOUND
      contentAsJson(deleteResult) shouldBe Json.toJson("could not delete file")

    }

  }



  //  override def beforeEach(): Unit = repository.deleteAll()
// AS MOCKING I AM NOT ACTUALLY INTERACTING WITH DATABASE SO DO NOT NEED TO CLEAR IT EACH TEST
//  override def afterEach(): Unit = repository.deleteAll()
}
