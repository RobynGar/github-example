package controllers
import data.Data
import models.{APIError, User}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import services.ApplicationService
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class ApplicationController @Inject()(val controllerComponents: ControllerComponents, val service: ApplicationService)(implicit val ec: ExecutionContext) extends BaseController {

//  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
//    Future.successful(Ok(views.html.user(Data.users)))
//  }

  def index(): Action[AnyContent] = Action.async { implicit request =>
    service.index().map{
      case Right(users: Seq[User])=> Ok(Json.toJson(users))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }


  def read(login: String): Action[AnyContent] = Action.async { implicit request =>
    service.read(login).map{
      case Right(user) => Ok(Json.toJson(user))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def readFromAPI(login: String): Action[AnyContent] = Action.async { implicit request =>
    service.getUser(login = login).map{
      case Right(user) => Ok(Json.toJson(user))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def addFromAPI(login: String): Action[AnyContent] = Action.async { implicit request =>
    service.addApiUser(login = login).map{
      case Right(user: User) => Created(Json.toJson(user))
      case Left(error: APIError) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def create(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    service.create(request).map {
      case Right(value) => Created(Json.toJson(value))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def update(login: String): Action[JsValue] = Action.async(parse.json)  { implicit request =>
    service.update(login, request).map{
      case Right(value) => Accepted(Json.toJson(value))
      case Left(value) => Status(value.httpResponseStatus)(Json.toJson(value.reason))
    }
  }

  def delete(login: String): Action[AnyContent] = Action.async { implicit request =>
    service.delete(login).map{
      case Right(value: String) => Accepted
      case Left(value) => Status(value.httpResponseStatus)(Json.toJson(value.reason))
    }
  }


  def showUser(login: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    service.getUser(login = login).map {
      case Right(user: User) => Ok(views.html.user(user))
      case Left(value) => Status(value.httpResponseStatus)(Json.toJson(value.reason))
    }
  }

  def usersRepos(login: String): Action[AnyContent] = Action.async { implicit request =>
    service.getUsersRepo(login = login).map{
      case Right(repo) => Ok(views.html.repositories(repo))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def usersRepoInfo(login: String, repoName: String): Action[AnyContent] = Action.async { implicit request =>
    service.getUsersRepoInfo(login = login, repoName = repoName).map{
      case Right(repo) => Ok(views.html.repoInfo(repo))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def repoContent(login: String, repoName: String): Action[AnyContent] = Action.async { implicit request =>
    service.getRepoContent(login = login, repoName = repoName).map{
      case Right(repo) => Ok(views.html.foldersAndFiles(repo, login, repoName))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def dirContent(dirName: String, login: String, repoName: String): Action[AnyContent] = Action.async { implicit request =>
    service.getDirContent(dirName= dirName, login = login, repoName = repoName).map{
      case Right(dir) => Ok(views.html.foldersAndFiles(dir, login, repoName))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def fileContent(filePath: String, login: String, repoName: String): Action[AnyContent] = Action.async { implicit request =>
    service.getFileContent(filePath= filePath, login = login, repoName = repoName).map{
      case Right(file) => Ok(views.html.file(file, login, repoName))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def createFile(login: String, repoName: String, filePath: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    service.createFile(login = login, repoName = repoName, filePath= filePath, newFile= request).map{
      case Right(file) => Ok(Json.toJson(file))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def updateFile(login: String, repoName: String, filePath: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    service.updateFile(filePath= filePath, login = login, repoName = repoName, updatedFile = request ).map{
      case Right(file) => Ok(Json.toJson(file))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def deleteFile(login: String, repoName: String, filePath: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    service.deleteFile(filePath= filePath, login = login, repoName = repoName, deleteCommitMessage = request).map{
      case Right(deleteConformation: String) => Accepted
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def repoReadMe(login: String, repoName: String): Action[AnyContent] = Action.async { implicit request =>
    service.repoReadMe(login, repoName).map{
      case Right(file) => Ok(views.html.file(file, login, repoName))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def dirReadMe(login: String, repoName: String, dir: String): Action[AnyContent] = Action.async { implicit request =>
    service.dirReadMe(login, repoName, dir).map{
      case Right(file) => Ok(views.html.file(file, login, repoName))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def downloadTar(login: String, repoName: String, branch: Option[String]): Action[AnyContent] = Action.async { implicit request =>
    service.downloadTar(login, repoName, branch).map{
      case Right(downloaded: Int) => Ok(Json.toJson("Downloaded"))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def downloadZip(login: String, repoName: String, branch: String): Action[AnyContent] = Action.async { implicit request =>
    service.downloadZip(login, repoName, branch).map{
      case Right(downloaded: Int) => Ok(Json.toJson("Downloaded"))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  }
