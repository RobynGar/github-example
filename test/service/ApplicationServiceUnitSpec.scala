package service

import baseSpec.BaseSpecWithApplication
import com.mongodb.internal.bulk.DeleteRequest
import connectors.ApplicationConnector
import models._
import org.scalamock.scalatest.MockFactory
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc.Request
import play.api.test.FakeRequest
import repositories.TraitDataRepo
import services.ApplicationService

import scala.concurrent.{ExecutionContext, Future}

class ApplicationServiceUnitSpec extends BaseSpecWithApplication with MockFactory{

  val mockRepository: TraitDataRepo = mock[TraitDataRepo]
  val mockConnector: ApplicationConnector = mock[ApplicationConnector]
  val TestApplicationService = new ApplicationService(
    mockConnector, mockRepository
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

  private val emptyUserSequence: Seq[User] = Seq()
  private val userSequence: Seq[User] = Seq(user)

  private val repo: Repository = Repository(
    "test",
    "test repo",
    false,
    "testURL",
    "testhtmlURL"
  )
  private val listOfFF: FFitems = FFitems(
    "test folder","dir", "app/service", "testurl", "shalala"
  )
  private val file: File = File(
    "test file", "fggtyhuyju", "file", "service/ApplicationService", "http://", "testurl", "base64", "decoded url"
  )

  "ApplicationService .index" should {

    "return when there are no users" in {
      (() => mockRepository.index()).expects().returning(Future(Right(emptyUserSequence))).once()

      whenReady(TestApplicationService.index()) { result =>
        result shouldBe Right(emptyUserSequence)
      }
    }

    "return sequence of users when there are users" in {

      (() => mockRepository.index()).expects().returning(Future(Right(userSequence))).once()

      whenReady(TestApplicationService.index()) { result =>
        result shouldBe Right(userSequence)
      }
    }
  }

  "ApplicationService() .create()" should {

    "should add user to mongodb" in {

      val request: FakeRequest[JsValue] = buildPost("/github/users/create").withBody[JsValue](Json.toJson(user))
      (mockRepository.create(_: User)).expects(user).returning(Future(Right(user))).once()

      whenReady(TestApplicationService.create(request)) { result =>
        result shouldBe Right(user)
      }
    }

    "could not validate user so should not add to mongodb or call create" in {

      val request: FakeRequest[JsValue] = buildPost("/github/users/create").withBody[JsValue](Json.obj())
     (mockRepository.create(_: User)).expects(*).returning(Future(Left(APIError.BadAPIResponse(400, "could not add user")))).never()

      whenReady(TestApplicationService.create(request)) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not add user"))
      }
    }

    "unable to create a user as with validate user format but login already exists" in {
      val request: FakeRequest[JsValue] = buildPost("/github/users/create").withBody[JsValue](Json.toJson(updatedUser))

      (mockRepository.create(_: User)).expects(*).returning(Future(Left(APIError.BadAPIResponse(400, "could not add user")))).once()

      whenReady(TestApplicationService.create(request)) {result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not add user"))
      }
    }
  }

  "ApplicationService .read()" should{

    "return a user by login/username" in{

      (mockRepository.read(_: String)).expects(*).returning(Future(Right(user))).once()

      whenReady(TestApplicationService.read("test")) { result =>
        result shouldBe Right(user)
      }
    }

    "does not return a user as incorrect login/username" in{

      (mockRepository.read(_: String)).expects(*).returning(Future(Left(APIError.BadAPIResponse(400, "could not find any users")))).once()

      whenReady(TestApplicationService.read("45")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not find any users"))
      }
    }
  }

  "ApplicationService .update()" should {

    "return an updated user" in {
      val request: FakeRequest[JsValue] = buildPut("/github/users/test").withBody[JsValue](Json.toJson(updatedUser))

      (mockRepository.update(_: String, _: User)).expects(*, *).returning(Future(Right(updatedUser))).once()

      whenReady(TestApplicationService.update("test", request)) {result =>
        result shouldBe Right(updatedUser)
      }
    }

    "unable to updated a user as cannot validate user format" in {
      val request: FakeRequest[JsValue] = buildPut("/github/users/45").withBody[JsValue](Json.obj())

      (mockRepository.update(_: String, _: User)).expects(*, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not update user information")))).never()

      whenReady(TestApplicationService.update("45", request)) {result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not update user information"))
      }
    }

    "unable to updated a user as with validate user format but non-existing login" in {
      val request: FakeRequest[JsValue] = buildPut("/github/users/45").withBody[JsValue](Json.toJson(updatedUser))

      (mockRepository.update(_: String, _: User)).expects(*, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not update user information")))).once()

      whenReady(TestApplicationService.update("45", request)) {result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not update user information"))
      }
    }
  }

  "ApplicationService .delete()" should {

    "return a string upon deleting a user by login" in {
      (mockRepository.delete(_: String)).expects(*).returning(Future(Right("deleted"))).once()

      whenReady(TestApplicationService.delete("test")) {result =>
        result shouldBe Right("deleted")
      }
    }

    "return a error upon unsuccessfully trying to delete a user by login" in {
      (mockRepository.delete(_: String)).expects(*).returning(Future(Left(APIError.BadAPIResponse(400, "could not delete user")))).once()

      whenReady(TestApplicationService.delete("45")) {result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not delete user"))
      }
    }
  }


  "ApplicationService .getUser()" should {

    "return a user by login for github api" in {
      (mockConnector.get[User](_: String)(_: OFormat[User], _: ExecutionContext)).expects(*, *, *).returning(Future(Right(apiUser))).once()

      whenReady(TestApplicationService.getUser(login = "test")) { result =>
        result shouldBe Right(apiUser)
      }
    }

    "unable to return a user from api, incorrect login" in {
      (mockConnector.get[User](_: String)(_: OFormat[User], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find user")))).once()

      whenReady(TestApplicationService.getUser(login = "45")) {result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not find user"))
      }
    }
  }

  "ApplicationService .getUsersRepo()" should {

    "return a user repositories names in list " in {
      (mockConnector.getUserRepo[Repository](_: String)(_: OFormat[Repository], _: ExecutionContext)).expects(*, *, *).returning(Future(Right(List("repo1", s"${repo.repoName}", "repo2")))).once()

      whenReady(TestApplicationService.getUsersRepo(login = "test")) { result =>
        result shouldBe Right(List("repo1", s"${repo.repoName}", "repo2"))
      }
    }

    "unable to return a user's repositories names from api, incorrect login" in {
      (mockConnector.getUserRepo[Repository](_: String)(_: OFormat[Repository], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user")))).once()

      whenReady(TestApplicationService.getUsersRepo(login = "45")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))
      }
    }
  }

  "ApplicationService .getUsersRepoInfo()" should {

    "return a users information by login for github api" in {
      (mockConnector.getUserRepoInfo[Repository](_: String)(_: OFormat[Repository], _: ExecutionContext)).expects(*, *, *).returning(Future(Right(repo))).once()

      whenReady(TestApplicationService.getUsersRepoInfo(login = "test", repoName= "test repo")) { result =>
        result shouldBe Right(repo)
      }
    }

    "unable to return a user information from api, incorrect login" in {
      (mockConnector.getUserRepoInfo[Repository](_: String)(_: OFormat[Repository], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories information")))).once()

      whenReady(TestApplicationService.getUsersRepoInfo(login = "45", repoName = "boop")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not find any repositories information"))
      }
    }
  }

  "ApplicationService .getRepoContent()" should {

    "return a user repositories content by login for github api" in {
      (mockConnector.getRepoContent[FFitems](_: String)(_: OFormat[FFitems], _: ExecutionContext)).expects(*, *, *).returning(Future(Right(Seq(listOfFF)))).once()

      whenReady(TestApplicationService.getRepoContent(login = "test", repoName= "test repo")) { result =>
        result shouldBe Right(Seq(listOfFF))
      }
    }

    "unable to return a user repositories content from api, incorrect login" in {
      (mockConnector.getRepoContent[FFitems](_: String)(_: OFormat[FFitems], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repositories information")))).once()

      whenReady(TestApplicationService.getRepoContent(login = "45", repoName = "boop")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not find any repositories information"))
      }
    }
  }

  "ApplicationService .getDirContent()" should {

    "return a user's repository folder contents by login for github api" in {
      (mockConnector.getDirContent[FFitems](_: String)(_: OFormat[FFitems], _: ExecutionContext)).expects(*, *, *).returning(Future(Right(Seq(listOfFF)))).once()

      whenReady(TestApplicationService.getDirContent(login = "test", repoName= "test repo", dirName = "service")) { result =>
        result shouldBe Right(Seq(listOfFF))
      }
    }

    "unable to return a user's repository folder content from api, incorrect login" in {
      (mockConnector.getDirContent[FFitems](_: String)(_: OFormat[FFitems], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find any repository files")))).once()

      whenReady(TestApplicationService.getDirContent(login = "45", repoName = "boop", dirName = "beep")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not find any repository files"))
      }
    }
  }

  "ApplicationService .getFileContent()" should {

    "return a user's repository folder contents by login for github api" in {
      (mockConnector.getFileContent[File](_: String)(_: OFormat[File], _: ExecutionContext)).expects(*, *, *).returning(Future(Right(file))).once()

      whenReady(TestApplicationService.getFileContent(filePath = "service/ApplicationService", login = "test", repoName= "test repo")) { result =>
        result shouldBe Right(file)
      }
    }

    "unable to return a user's repository folder content from api, incorrect login" in {
      (mockConnector.getFileContent[File](_: String)(_: OFormat[File], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find any file contents")))).once()

      whenReady(TestApplicationService.getFileContent(filePath = "service/beep", login = "45", repoName = "beep")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not find any file contents"))
      }
    }
  }

  "ApplicationService .addApiUser()" should {

    "add a user from github api to mongodb" in {
      (mockConnector.get[User](_: String)(_: OFormat[User], _: ExecutionContext)).expects(*, *, *).returning(Future(Right(apiUser))).once()
      (mockRepository.create(_: User)).expects(*).returning(Future(Right(apiUser))).once()

      whenReady(TestApplicationService.addApiUser(login = "test")) { result =>
        result shouldBe Right(apiUser)
      }
    }

    "unable to return a user from api, incorrect login" in {
      (mockConnector.get[User](_: String)(_: OFormat[User], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find user")))).once()
      (mockRepository.create(_: User)).expects(*).returning(Future(Right(apiUser))).never()

      whenReady(TestApplicationService.addApiUser(login = "45")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not add user"))
      }
    }
  }

  "ApplicationService .deleteFile()" should {

    "add a user from github api to mongodb" in {

      val request: FakeRequest[JsValue] = buildDelete("/github/users/test/repos/testRepo/file/delete/testFile").withBody[JsValue](Json.toJson("delete message"))

      (mockConnector.deleteFile[DeletedReturn](_: String, _: RequestDelete)(_: OFormat[DeletedReturn], _: ExecutionContext))
        .expects(*, *, *, executionContext)
        .returning(Future(Right(DeletedReturn(None))))
        .once()
      (mockConnector.getFileContent[File](_: String)(_: OFormat[File], _: ExecutionContext))
        .expects(*, *, *)
        .returning(Future(Right(file)))
        .once()

      whenReady(TestApplicationService.deleteFile("test", "testRepo", "testFile", request)) { result =>
        result shouldBe Right(DeletedReturn(None))
      }
    }

//    "unable to return a user from api, incorrect login" in {
//      (mockConnector.get[User](_: String)(_: OFormat[User], _: ExecutionContext)).expects(*, *, *).returning(Future(Left(APIError.BadAPIResponse(400, "could not find user")))).once()
//      (mockRepository.create(_: User)).expects(*).returning(Future(Right(apiUser))).never()
//
//      whenReady(TestApplicationService.addApiUser(login = "45")) { result =>
//        result shouldBe Left(APIError.BadAPIResponse(400, "could not add user"))
//      }
//    }
  }

}


