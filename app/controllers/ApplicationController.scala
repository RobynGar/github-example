package controllers
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.ApplicationService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApplicationController @Inject()(val controllerComponents: ControllerComponents, service: ApplicationService)(implicit val ec: ExecutionContext) extends BaseController {

  val users = Map.empty[String, models.User]

  def index(): Action[AnyContent] = Action.async { implicit request =>
    val models = users.values
    val json = Json.toJson(models)

    Future(Ok(json))
  }

  def read(login: String): Action[AnyContent] = Action.async { implicit request =>
    service.getUser(login = login).map{
      case Right(user) => Ok(Json.toJson(user))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def create(): Action[AnyContent] = Action.async { implicit request =>
    ???
  }

  def update(login: String): Action[AnyContent] = Action.async { implicit request =>
    ???
  }

  def delete(login: String): Action[AnyContent] = Action.async { implicit request =>
    ???
  }

}
