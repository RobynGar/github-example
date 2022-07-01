package services

import connectors.ApplicationConnector
import models.{APIError, User}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApplicationService @Inject()(connector: ApplicationConnector) {

  def getUser(urlOverride: Option[String] = None, login: String)(implicit ec: ExecutionContext): Future[Either[APIError, User]]  ={
    connector.get[User](urlOverride.getOrElse(s"https://api.github.com/users/$login"))

  }

  def getAllUsers(urlOverride: Option[String] = None)(implicit ec: ExecutionContext): Unit ={
    connector.getAll[Seq[User]](urlOverride.getOrElse(s"https://api.github.com/users"))

  }

}
