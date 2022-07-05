package controllers
import models.{APIError, User}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.ApplicationService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class ApplicationController @Inject()(val controllerComponents: ControllerComponents, service: ApplicationService)(implicit val ec: ExecutionContext) extends BaseController {

  //val users = Map.empty[String, models.User]

  def index(): Action[AnyContent] = Action.async { implicit request =>
//    val models = users.values
//    val json = Json.toJson(models)
//    Future(Ok(json))
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
    service.addUser(login = login)
      .map{
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
      case Right(value) => Accepted
      case Left(value) => Status(value.httpResponseStatus)(Json.toJson(value.reason))
    }
  }

}
