package controllers

import baseSpec.BaseSpecWithApplication
import play.api.http.Status
import models.{CreateFile, RequestDelete, User}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContentAsEmpty, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers.{DELETE, GET, POST, PUT, contentAsJson, contentAsString, contentType, defaultAwaitTimeout, route, status, writeableOf_AnyContentAsEmpty}

import java.net.URLEncoder
import scala.concurrent.Future

class ApplicationControllerSpec extends BaseSpecWithApplication {

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

  private val createFile: CreateFile = CreateFile(
    "add readme",
    "read me"
  )

  private val updateFile: CreateFile = CreateFile(
    "test update method",
    "new content"
  )


  "ApplicationController .index" should {

    "return when there are no users" in {
      val result = TestApplicationController.index()(FakeRequest())
      status(result) shouldBe Status.OK
    }

    "return all users in the database" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/github/users").withBody[JsValue](Json.toJson(user))
      val readAllRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users")
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val readAllResult: Future[Result] = TestApplicationController.index()(readAllRequest)

      status(createdResult) shouldBe Status.CREATED
      contentAsJson(createdResult).as[User] shouldBe user
      status(readAllResult) shouldBe Status.OK
      contentAsJson(readAllResult).as[Seq[User]] shouldBe Seq(user)
      afterEach()
    }

    "return json from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users")
      val response = route(app, apiRequest).get

      status(response) shouldBe Status.OK
      contentType(response) shouldBe Some("application/json")

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

    "return json from the router" in {
      beforeEach()
      val apiRequest = FakeRequest(POST, "/github/users/create").withBody[JsValue](Json.toJson(user))
      val response = route(app, apiRequest).get

      status(response) shouldBe Status.CREATED
      contentType(response) shouldBe Some("application/json")
      beforeEach()
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

    "return json from the router" in {
      beforeEach()
      val apiCreateRequest = FakeRequest(POST, "/github/users/create").withBody[JsValue](Json.toJson(user))
      val apiReadRequest = FakeRequest(GET, "/github/users/test")
      val createResponse = route(app, apiCreateRequest).get
      val readResponse = route(app, apiReadRequest).get

      status(createResponse) shouldBe Status.CREATED
      contentType(createResponse) shouldBe Some("application/json")
      status(readResponse) shouldBe Status.OK
      contentType(readResponse) shouldBe Some("application/json")
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

    "return json from the router" in {
      beforeEach()
      val apiCreateRequest = FakeRequest(POST, "/github/users/create").withBody[JsValue](Json.toJson(user))
      val apiUpdateRequest = FakeRequest(PUT, "/github/users/test").withBody[JsValue](Json.toJson(updatedUser))
      val createResponse = route(app, apiCreateRequest).get
      val updateResponse = route(app, apiUpdateRequest).get

      status(createResponse) shouldBe Status.CREATED
      contentType(createResponse) shouldBe Some("application/json")
      status(updateResponse) shouldBe Status.ACCEPTED
      contentType(updateResponse) shouldBe Some("application/json")
      afterEach()
    }

  }

  "ApplicationController .delete()" should {

    "find a user in the database by login and delete" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/github/users").withBody[JsValue](Json.toJson(user))
      val deleteRequest: FakeRequest[AnyContentAsEmpty.type] = buildDelete("/github/users/test")
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

    "return accepted status from the router" in {
      beforeEach()
      val apiCreateRequest = FakeRequest(POST, "/github/users/create").withBody[JsValue](Json.toJson(user))
      val apiDeleteRequest =  FakeRequest(DELETE, "/github/users/test")
      val createResponse = route(app, apiCreateRequest).get
      val deleteResponse = route(app, apiDeleteRequest).get

      status(createResponse) shouldBe Status.CREATED
      contentType(createResponse) shouldBe Some("application/json")
      status(deleteResponse) shouldBe Status.ACCEPTED

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


  "ApplicationController .showUser()" should {

    "use username/login to find user from api and return that user" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/userspage/bla")
      val apiResult = TestApplicationController.showUser("bla")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentType(apiResult) shouldBe Some("text/html")

    }

    "use incorrect username/login return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/userspage/meeptot")
      val apiResult = TestApplicationController.showUser("meeptot")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find user")

    }

    "render the userPage from the router" in {
      val apiRequest = FakeRequest(GET, "/github/userspage/bla")
      val userPage = route(app, apiRequest).get

      status(userPage) shouldBe Status.OK
      contentType(userPage) shouldBe Some("text/html")

    }

    "unable to render the userPage incorrect username from the router" in {
      val apiRequest = FakeRequest(GET, "/github/userspage/meeptot")
      val notUserPage = route(app, apiRequest).get

      status(notUserPage) shouldBe Status.BAD_REQUEST
      contentAsJson(notUserPage) shouldBe Json.toJson("could not find user")

    }

  }

  "ApplicationController .usersRepos()" should {

    "login to find user's repositories and return their names in list " in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/bla/repos")
      val apiResult = TestApplicationController.usersRepos("bla")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentType(apiResult) shouldBe Some("text/html")

    }

    "use incorrect login return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos")
      val apiResult = TestApplicationController.usersRepos("meeptot")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories associated with that user")

    }

    "use login for user with no repositories, return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meep/repos")
      val apiResult = TestApplicationController.usersRepos("meep")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories associated with that user")

    }


    "render the repositories page from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/bla/repos")
      val repoPage = route(app, apiRequest).get

      status(repoPage) shouldBe Status.OK
      contentType(repoPage) shouldBe Some("text/html")

    }

    "unable to render the repositories Page due to incorrect username from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/meeptot/repos")
      val notrepoPage = route(app, apiRequest).get

      status(notrepoPage) shouldBe Status.BAD_REQUEST
      contentAsJson(notrepoPage) shouldBe Json.toJson("could not find any repositories associated with that user")

    }

  }

  "ApplicationController() .usersRepoInfo()" should {

    "login to find user's repositories under the name specified and return info about that repo " in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/bla/android-runner")
      val apiResult = TestApplicationController.usersRepoInfo("bla", "android-runner")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentType(apiResult) shouldBe Some("text/html")

    }

    "use incorrect login return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/test")
      val apiResult = TestApplicationController.usersRepoInfo("meeptot", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories information")

    }

    "use login for user with no repositories, return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meep/test")
      val apiResult = TestApplicationController.usersRepoInfo("meep", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repositories information")

    }


    "render the repoInfo page from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/bla/android-runner")
      val repoInfoPage = route(app, apiRequest).get

      status(repoInfoPage) shouldBe Status.OK
      contentType(repoInfoPage) shouldBe Some("text/html")

    }

    "unable to render the repositories Page due to incorrect username from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/meeptot/test")
      val notrepoInfoPage = route(app, apiRequest).get

      status(notrepoInfoPage) shouldBe Status.BAD_REQUEST
      contentAsJson(notrepoInfoPage) shouldBe Json.toJson("could not find any repositories information")

    }

  }

  "ApplicationController() .repoContent()" should {

    "login to find user's repositories under the name specified and return info about that repo " in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/bla/repos/android-runner")
      val apiResult = TestApplicationController.repoContent("bla", "android-runner")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentType(apiResult) shouldBe Some("text/html")

    }

    "use incorrect login return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos/test")
      val apiResult = TestApplicationController.repoContent("meeptot", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repository files")

    }

    "use login for user with no repositories, return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meep/repos/test")
      val apiResult = TestApplicationController.repoContent("meep", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repository files")

    }


    "render the repoInfo page from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/bla/repos/android-runner")
      val repoInfoPage = route(app, apiRequest).get

      status(repoInfoPage) shouldBe Status.OK
      contentType(repoInfoPage) shouldBe Some("text/html")

    }

    "unable to render the repositories Page due to incorrect username from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/meeptot/repos/test")
      val notrepoInfoPage = route(app, apiRequest).get

      status(notrepoInfoPage) shouldBe Status.BAD_REQUEST
      contentAsJson(notrepoInfoPage) shouldBe Json.toJson("could not find any repository files")

    }

  }


  "ApplicationController() .dirContent()" should {

    "login to find user's repositories under the name specified and return folders and files form that repo " in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/bla/repos/android-runner/MonkeyPlayer")
      val apiResult = TestApplicationController.dirContent("MonkeyPlayer", "bla", "android-runner")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentType(apiResult) shouldBe Some("text/html")

    }

    "use incorrect login return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos/test/testFolder")
      val apiResult = TestApplicationController.dirContent("testFolder", "meeptot", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repository files")

    }

    "use login for user with no repositories, return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meep/repos/test/testFolder")
      val apiResult = TestApplicationController.dirContent("testFolder", "meep", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any repository files")

    }


    "render the foldersAndFiles page from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/bla/repos/android-runner/MonkeyPlayer")
      val page = route(app, apiRequest).get

      status(page) shouldBe Status.OK
      contentType(page) shouldBe Some("text/html")

    }

    "unable to render the foldersAndFiles Page due to incorrect username from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/meeptot/repos/test/testFolder")
      val notPage = route(app, apiRequest).get

      status(notPage) shouldBe Status.BAD_REQUEST
      contentAsJson(notPage) shouldBe Json.toJson("could not find any repository files")

    }

  }

  "ApplicationController() .fileContent()" should {

    "login to find user's repositories under the name specified and return folders and files form that repo " in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/bla/repos/android-runner/file/MonkeyPlayer/README.md")
      val apiResult = TestApplicationController.fileContent("MonkeyPlayer/README.md", "bla", "android-runner")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentType(apiResult) shouldBe Some("text/html")

    }

    "use incorrect login return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meeptot/repos/test/file/testFolder/testFile")
      val apiResult = TestApplicationController.fileContent("testFolder/testFile", "meeptot", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any file contents")

    }

    "use login for user with no repositories, return api error" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/meep/repos/test/file/testFolder/testFile")
      val apiResult = TestApplicationController.fileContent("testFolder/testFile", "meep", "test")(apiRequest)

      status(apiResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResult) shouldBe Json.toJson("could not find any file contents")

    }


    "render the foldersAndFiles page from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/bla/repos/android-runner/file/" + URLEncoder.encode("MonkeyPlayer/README.md", "UTF-8"))
      val page = route(app, apiRequest).get

      status(page) shouldBe Status.OK
      contentType(page) shouldBe Some("text/html")

    }

    "unable to render the foldersAndFiles Page due to incorrect username from the router" in {
      val apiRequest = FakeRequest(GET, "/github/users/meeptot/repos/test/file/" + URLEncoder.encode("testFolder/testFile", "UTF-8"))
      val notPage = route(app, apiRequest).get

      status(notPage) shouldBe Status.BAD_REQUEST
      contentAsJson(notPage) shouldBe Json.toJson("could not find any file contents")
    }

  }

  "ApplicationController() .createFile()" should {

    "login to find user's repositories under the name specified and create file" in {

      val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/create/newFile3.txt").withBody[JsValue](Json.toJson(createFile))
      val apiCreatedResult = TestApplicationController.createFile("RobynGar", "git_practice", "newFile3.txt")(apiCreatedRequest)

      status(apiCreatedResult) shouldBe Status.OK

      val apiDeleteRequest: FakeRequest[JsValue] = buildDelete("/github/users/RobynGar/repos/git_practice/file/delete/newFile3.txt").withBody[JsValue](Json.toJson("delete file"))
      val apiDeleteResult = TestApplicationController.deleteFile("RobynGar", "git_practice", "newFile3.txt")(apiDeleteRequest)

      status(apiDeleteResult) shouldBe Status.ACCEPTED
    }

    "create folder and file" in {

      val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/create/app/newFile3.txt").withBody[JsValue](Json.toJson(createFile))
      val apiCreatedResult = TestApplicationController.createFile("RobynGar", "git_practice", "app/newFile3.txt")(apiCreatedRequest)

      status(apiCreatedResult) shouldBe Status.OK

      val apiDeleteRequest: FakeRequest[JsValue] = buildDelete("/github/users/RobynGar/repos/git_practice/file/delete/app/newFile3.txt").withBody[JsValue](Json.toJson("delete file"))
      val apiDeleteResult = TestApplicationController.deleteFile("RobynGar", "git_practice", "app/newFile3.txt")(apiDeleteRequest)

      status(apiDeleteResult) shouldBe Status.ACCEPTED
    }

    "correct login but repository does not exist" in {
      val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/made-up-repo/file/create/newFile.txt").withBody[JsValue](Json.toJson(createFile))
      val apiCreatedResult = TestApplicationController.createFile("RobynGar", "made-up-repo", "newFile.txt")(apiCreatedRequest)

      status(apiCreatedResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiCreatedResult) shouldBe Json.toJson("could not create file")
    }

    "unable to validate file trying to be created" in {
      val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/create/newFile.txt").withBody[JsValue](Json.obj())
      val apiCreatedResult = TestApplicationController.createFile("RobynGar", "git_practice", "newFile.txt")(apiCreatedRequest)

      status(apiCreatedResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiCreatedResult) shouldBe Json.toJson("could not validate file")
    }

    "correct route for creating a file" in {
      val apiRequest = FakeRequest(PUT, "/github/users/RobynGar/repos/git_practice/file/create/newFile.txt").withBody[JsValue](Json.toJson(createFile))
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.OK
    }

    "route for creating a file with non-existing repository" in {
      val apiRequest = FakeRequest(PUT, "/github/users/RobynGar/repos/made-up/file/create/newFile.txt").withBody[JsValue](Json.toJson(createFile))
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.BAD_REQUEST
    }
  }

  "ApplicationController() .updateFile()" should {

    "login to find user's repositories under the name specified and update file" in {
      val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/create/newFile.txt").withBody[JsValue](Json.toJson(createFile))
      val apiCreatedResult = TestApplicationController.createFile("RobynGar", "git_practice", "newFile.txt")(apiCreatedRequest)

      status(apiCreatedResult) shouldBe Status.OK

      val apiUpdateRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/update/newFile.txt").withBody[JsValue](Json.toJson(updateFile))
      val apiUpdateResult = TestApplicationController.updateFile("RobynGar", "git_practice", "newFile.txt")(apiUpdateRequest)

      status(apiUpdateResult) shouldBe Status.OK

    }

    "correct login and repository but file does not exist" in {
      val apiUpdatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/update/nonExistent.txt").withBody[JsValue](Json.toJson(updateFile))
      val apiUpdatedResult = TestApplicationController.updateFile("RobynGar", "git_practice", "nonExistent.txt")(apiUpdatedRequest)

      status(apiUpdatedResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiUpdatedResult) shouldBe Json.toJson("could not update file")
    }

    "unable to validate file trying to be created" in {
      val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/update/newFile.txt").withBody[JsValue](Json.obj())
      val apiCreatedResult = TestApplicationController.updateFile("RobynGar", "git_practice", "newFile.txt")(apiCreatedRequest)

      status(apiCreatedResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiCreatedResult) shouldBe Json.toJson("could not validate file")
    }


    "correct route for updating a file" in {
      val apiRequest = FakeRequest(PUT, "/github/users/RobynGar/repos/git_practice/file/update/newFile.txt").withBody[JsValue](Json.toJson(updateFile))
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.OK
    }

    "route for creating a file with non-existing repository" in {
      val apiRequest = FakeRequest(PUT, "/github/users/RobynGar/repos/made-up/file/update/newFile.txt").withBody[JsValue](Json.toJson(updateFile))
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.BAD_REQUEST
    }
  }

  "ApplicationController() .deleteFile()" should {

    "login to find user's repositories under the name specified and delete file" in {
      val apiCreatedRequest: FakeRequest[JsValue] = buildPut("/github/users/RobynGar/repos/git_practice/file/create/newFile.txt").withBody[JsValue](Json.toJson(createFile))
      val apiCreatedResult = TestApplicationController.createFile("RobynGar", "git_practice", "newFile.txt")(apiCreatedRequest)

      status(apiCreatedResult) shouldBe Status.OK

      val apiDeleteRequest: FakeRequest[JsValue] = buildDelete("/github/users/RobynGar/repos/git_practice/file/delete/newFile.txt").withBody[JsValue](Json.toJson("delete file"))
      val apiDeleteResult = TestApplicationController.deleteFile("RobynGar", "git_practice", "newFile.txt")(apiDeleteRequest)

      status(apiDeleteResult) shouldBe Status.ACCEPTED
    }

    "correct login and repository but file does not exist" in {
      val apiDeleteRequest: FakeRequest[JsValue] = buildDelete("/github/users/RobynGar/repos/git_practice/file/delete/nonExistent.txt").withBody[JsValue](Json.toJson("delete file"))
      val apiDeleteResult = TestApplicationController.deleteFile("RobynGar", "git_practice", "nonExistent.txt")(apiDeleteRequest)

      status(apiDeleteResult) shouldBe Status.NOT_FOUND
      contentAsJson(apiDeleteResult) shouldBe Json.toJson("could not delete file")
    }

    "correct route for deleting a file" in {

      val apiDeleteRequest = FakeRequest(DELETE, "/github/users/RobynGar/repos/git_practice/file/delete/newFile.txt").withBody[JsValue](Json.toJson("delete file"))
      val apiDeleteResponse = route(app, apiDeleteRequest).get
      status(apiDeleteResponse) shouldBe Status.ACCEPTED
    }

    "route for deleting a file with non-existing repository" in {
      val apiRequest = FakeRequest(PUT, "/github/users/RobynGar/repos/made-up/file/delete/newFile.txt").withBody[JsValue](Json.toJson("delete file"))
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.NOT_FOUND
    }
  }

  "ApplicationController() .repoReadMe()" should {

    "login to find user's repositories under the name specified and get readMe" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/play-template/get/readme")
      val apiResult = TestApplicationController.repoReadMe("RobynGar", "play-template")(apiRequest)

      status(apiResult) shouldBe Status.OK
    }

    "correct login and repository but no readme exists" in {
      val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/git_practice/get/readme")
      val apiReadMeResult = TestApplicationController.repoReadMe("RobynGar", "git_practice")(apiReadMeRequest)

      status(apiReadMeResult) shouldBe Status.BAD_REQUEST
    }

    "correct route for getting a repositories readme" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/play-template/get/readme")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.OK
      contentType(apiResponse) shouldBe Some("text/html")
    }

    "correct route for getting a repositories readme but the repo has no readme" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/git_practice/get/readme")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.BAD_REQUEST
    }

    "route for getting readme with non-existing repository" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/made-up/get/readme")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.BAD_REQUEST
    }
  }

  "ApplicationController() .dirReadMe()" should {

    "login to find user's repositories and get readMe from specified directory" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/play-template/readme/app")
      val apiResult = TestApplicationController.dirReadMe("RobynGar", "play-template", "app")(apiRequest)

      status(apiResult) shouldBe Status.OK
      contentType(apiResult) shouldBe Some("text/html")
    }

     "correct login and repository but no readme exists in that directory" in {
       val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/git_practice/get/readme/project")
       val apiReadMeResult = TestApplicationController.dirReadMe("RobynGar", "git_practice", "project")(apiReadMeRequest)

       status(apiReadMeResult) shouldBe Status.NOT_FOUND
       contentAsJson(apiReadMeResult) shouldBe Json.toJson("resource not found")

     }

    "correct route for getting a directories readme" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/play-template/get/readme/app")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.OK
      contentType(apiResponse) shouldBe Some("text/html")
    }

    "correct route for getting a repositories readme but the repo has no readme" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/git_practice/get/readme/project")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.NOT_FOUND
      contentAsJson(apiResponse) shouldBe Json.toJson("resource not found")

    }

    "route for getting readme with non-existing repository" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/made-up/get/readme/nodir")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.NOT_FOUND
      contentAsJson(apiResponse) shouldBe Json.toJson("resource not found")
    }
  }

  "ApplicationController() .downloadTar()" should {

    "download the tar of the repository specified form the branch specified" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/git_practice/download/tarball/main")
      val apiResult = TestApplicationController.downloadTar("RobynGar", "git_practice", Some("main"))(apiRequest)

      status(apiResult) shouldBe Status.OK
    }

    "return ok http response for tar download from the router and when no branch provided default to main" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/git_practice/download/tarball")
      val response = route(app, apiRequest).get

      status(response) shouldBe Status.OK
    }

    "return ok http response for tar download from the router and when a branch is provided" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/git_practice/download/tarball?branch=main")
      val response = route(app, apiRequest).get

      status(response) shouldBe Status.OK
      contentAsJson(response) shouldBe Json.toJson("Downloaded")

    }

    "correct login but no repository exists" in {
      val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/no-such-repo/download/tarball")
      val apiReadMeResult = TestApplicationController.downloadTar("RobynGar", "no-such-repo", None)(apiReadMeRequest)

      status(apiReadMeResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiReadMeResult) shouldBe Json.toJson("could not download tar")
    }

    "route for getting readme with non-existing repository" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/made-up/download/tarball")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResponse) shouldBe Json.toJson("could not download tar")
    }
  }

  "ApplicationController() .downloadZip()" should {

    "download the tar of the repository specified form the branch specified" in {

      val apiRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/github/users/RobynGar/repos/git_practice/download/zipball")
      val apiResult = TestApplicationController.downloadZip("RobynGar", "git_practice", "main")(apiRequest)

      status(apiResult) shouldBe Status.OK
    }

    "return ok http response for zip download from the router and when no branch provided default to main" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/git_practice/download/zipball")
      val response = route(app, apiRequest).get

      status(response) shouldBe Status.OK
    }

    "return ok http response for zip download from the router and when a branch is provided" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/git_practice/download/zipball?branch=main")
      val response = route(app, apiRequest).get

      status(response) shouldBe Status.OK
    }

    "correct login but no repository exists" in {
      val apiReadMeRequest: FakeRequest[AnyContentAsEmpty.type] = buildPut("/github/users/RobynGar/repos/no-such-repo/download/zipball")
      val apiReadMeResult = TestApplicationController.downloadZip("RobynGar", "no-such-repo", "main")(apiReadMeRequest)

      status(apiReadMeResult) shouldBe Status.BAD_REQUEST
      contentAsJson(apiReadMeResult) shouldBe Json.toJson("could not download zip")
    }

    "route for getting readme with non-existing repository" in {
      val apiRequest = FakeRequest(GET, "/github/users/RobynGar/repos/made-up/download/zipball")
      val apiResponse = route(app, apiRequest).get

      status(apiResponse) shouldBe Status.BAD_REQUEST
      contentAsJson(apiResponse) shouldBe Json.toJson("could not download zip")
    }

  }


  override def beforeEach(): Unit = repository.deleteAll()
  override def afterEach(): Unit = repository.deleteAll()

}
