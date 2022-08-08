package controllers

import baseSpec.BaseSpecWithApplication
import models.{APIError, Content, ReturnCreatedFile, User}
import org.scalamock.scalatest.MockFactory
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContent, AnyContentAsEmpty, Request, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsJson, contentType, defaultAwaitTimeout, route, status, writeableOf_AnyContentAsEmpty}
import services.ApplicationService

import scala.concurrent.Future

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



  //  override def beforeEach(): Unit = repository.deleteAll()
// AS MOCKING I AM NOT ACTUALLY INTERACTING WITH DATABASE SO DO NOT NEED TO CLEAR IT EACH TEST
//  override def afterEach(): Unit = repository.deleteAll()
}
