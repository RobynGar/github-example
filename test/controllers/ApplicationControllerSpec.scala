package controllers

import baseSpec.BaseSpecWithApplication
import play.api.http.Status
import models.User
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContentAsEmpty, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}

import scala.concurrent.Future

class ApplicationControllerSpec extends BaseSpecWithApplication{

  val TestApplicationController = new ApplicationController(
    component, service
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


  "ApplicationController .index" should {

    "return" in{
      val result = TestApplicationController.index()(FakeRequest())
      status(result) shouldBe Status.OK
    }
  }

  "ApplicationController .create()" should {

    "create a user in the database" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildPost("/github/users/create").withBody[JsValue](Json.toJson(user))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      status(createdResult) shouldBe Status.CREATED
      contentAsJson(createdResult).as[User] shouldBe user
      afterEach()

    }

    "try to create a user in the database with wrong format" in {
      beforeEach()
      val request = buildPost("/github/users").withBody[JsValue](Json.obj())
      val createdResult = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.BAD_REQUEST
      contentAsJson(createdResult) shouldBe Json.toJson("could not add user")
      afterEach()
    }

  }

  "ApplicationController .read()" should {

    "find a user in the database by login" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/github/users").withBody[JsValue](Json.toJson(user))
      val readRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/test")
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val readResult: Future[Result] = TestApplicationController.read("test")(readRequest)

      status(createdResult) shouldBe Status.CREATED
      contentAsJson(createdResult).as[User] shouldBe user
      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[User] shouldBe user
      afterEach()
    }

    "cannot find a user in the database as login does not exist" in {
      beforeEach()
      val readRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/5")
      val readResult: Future[Result] = TestApplicationController.read("5")(readRequest)

      status(readResult) shouldBe Status.BAD_REQUEST
      contentAsJson(readResult) shouldBe Json.toJson("could not find any users")
      afterEach()
    }

  }

  "ApplicationController .readAll()" should {

    "return all users in the database" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/github/users").withBody[JsValue](Json.toJson(user))
      val readAllRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users")
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val readAllResult: Future[Result] = TestApplicationController.readAll()(readAllRequest)

      status(createdResult) shouldBe Status.CREATED
      contentAsJson(createdResult).as[User] shouldBe user
      status(readAllResult) shouldBe Status.OK
      contentAsJson(readAllResult).as[Seq[User]] shouldBe Seq(user)
      afterEach()
    }

  }

  "ApplicationController .update()" should {

    "find a user in the database by login and update all fields" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/github/users").withBody[JsValue](Json.toJson(user))
      val updateRequest: FakeRequest[JsValue] = buildPut("/github/users/test").withBody[JsValue](Json.toJson(updatedUser))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val updateResult: Future[Result] = TestApplicationController.update("test")(updateRequest)

      status(createdResult) shouldBe Status.CREATED
      status(updateResult) shouldBe Status.ACCEPTED
      contentAsJson(updateResult).as[User] shouldBe updatedUser
      afterEach()
    }

    "try update a user by an login that does not exist and in a non-conforming format" in {
      beforeEach()
      val updateRequest = buildPut("/github/users/meep").withBody[JsValue](Json.obj())

      val updateResult = TestApplicationController.update("meep")(updateRequest)

      status(updateResult) shouldBe Status.BAD_REQUEST
      contentAsJson(updateResult) shouldBe Json.toJson("could not update user information")
      afterEach()
    }

  }

  "ApplicationController .delete()" should {

    "find a user in the database by login and delete" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/github/users").withBody[JsValue](Json.toJson(user))
      val deleteRequest: FakeRequest[AnyContentAsEmpty.type ] = buildDelete("/github/users/test")
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val deleteResult: Future[Result] = TestApplicationController.delete("test")(deleteRequest)

      status(createdResult) shouldBe Status.CREATED
      status(deleteResult) shouldBe Status.ACCEPTED
      afterEach()
    }

    "cannot find a user in the database as login does not exist do not delete" in {
      beforeEach()
      val deleteRequest: FakeRequest[AnyContentAsEmpty.type] = buildDelete("/github/users/meep")

      val deleteResult: Future[Result] = TestApplicationController.delete("meep")(deleteRequest)

      status(deleteResult) shouldBe Status.BAD_REQUEST
      contentAsJson(deleteResult) shouldBe Json.toJson("could not delete user")
      afterEach()
    }

  }

  "ApplicationController .readFromAPI()" should {

    "use username/login to find user from api and return that user" in {
      beforeEach()

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/api/bla")
      val apiResult = TestApplicationController.readFromAPI("bla")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentAsJson(apiResult) shouldBe Json.toJson(apiUser)


      afterEach()
    }

    "unknown username/login used to find user from api cannot return that user" in {
      beforeEach()

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/api/meeptot")
      val apiResult = TestApplicationController.readFromAPI("meeptot")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find user")


      afterEach()
    }
  }

  "ApplicationController .addFromAPI()" should {

    "use username/login to find user from api and return that user" in {
      beforeEach()

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/add/bla")
      val apiResult = TestApplicationController.addFromAPI("bla")(apiRequest)

      status(apiResult) shouldBe Status.CREATED
      contentAsJson(apiResult) shouldBe Json.toJson(apiUser)


      afterEach()
    }

    "unknown username/login used to find user from api cannot return that user" in {
      beforeEach()

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/add/meeptot")
      val apiResult = TestApplicationController.addFromAPI("meeptot")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not add user")


      afterEach()
    }
  }

  override def beforeEach(): Unit = repository.deleteAll()
  override def afterEach(): Unit = repository.deleteAll()

}
