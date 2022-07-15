package services

import connectors.ApplicationConnector
import models.File.jsonReads
import models.{APIError, FFitems, File, Repository, User}
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.Request
import repositories.DataRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApplicationService @Inject()(connector: ApplicationConnector, dataRepository: DataRepository)(implicit ec: ExecutionContext) {

  def getUser(urlOverride: Option[String] = None, login: String)(implicit ec: ExecutionContext): Future[Either[APIError, User]] = {
    connector.get[User](urlOverride.getOrElse(s"https://api.github.com/users/$login"))
  }

//  def getRepoContent(urlOverride: Option[String] = None, login: String, repo: String)(implicit ec: ExecutionContext) = {
//    connector.getRepoContent[Repositories](urlOverride.getOrElse(s"https://api.github.com/repos/$login/$repo/contents"))
//  }

  def getUsersRepo(urlOverride: Option[String] = None, login: String)(implicit ec: ExecutionContext): Future[Either[APIError, List[String]]] = {
    connector.getUserRepo[Repository](urlOverride.getOrElse(s"https://api.github.com/users/$login/repos"))
  }

  def getUsersRepoInfo(urlOverride: Option[String] = None, login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, Repository]] = {
    connector.getUserRepoInfo[Repository](urlOverride.getOrElse(s"https://api.github.com/repos/$login/$repoName"))
  }

  def getRepoContent(urlOverride: Option[String] = None, login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, Seq[FFitems]]] = {
    connector.getRepoContent[FFitems](urlOverride.getOrElse(s"https://api.github.com/repos/$login/$repoName/contents"))
  }

  def getDirContent(urlOverride: Option[String] = None, dirName: String, login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, Seq[FFitems]]] = {
    connector.getDirContent[FFitems](urlOverride.getOrElse(s"https://api.github.com/repos/$login/$repoName/contents/$dirName"))
  }

  def getFileContent(urlOverride: Option[String] = None, filePath: String, login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, File]] = {
    connector.getFileContent[File](urlOverride.getOrElse(s"https://api.github.com/repos/$login/$repoName/contents/$filePath"))
  }

  def index(): Future[Either[APIError, Seq[User]]] = {
    dataRepository.index().map {
      case Right(users: Seq[User]) => Right(users)
      case Left(value) => Left(value)
    }
  }

  def read(login: String): Future[Either[APIError, User]] = {
    dataRepository.read(login).map {
      case Right(users: User) => Right(users)
      case Left(value) => Left(value)
    }
  }

  def create(input: Request[JsValue]): Future[Either[APIError, User]] = {
    input.body.validate[User] match {
      case JsSuccess(value, _) => dataRepository.create(value)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "could not add user")))
    }
  }


  def addApiUser(login: String):Future[Either[APIError, User]]= {
    getUser(login = login) flatMap  {
      case Right(user: User) => dataRepository.create(user)
      case Left(error) => Future(Left(APIError.BadAPIResponse(400, "could not add user")))
    }
  }

  def update(login: String, input: Request[JsValue]): Future[Either[APIError, User]] = {
    input.body.validate[User] match {
      case JsSuccess(user, _) => dataRepository.update(login, user)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "could not update user information")))
    }
  }

  def delete(login: String): Future[Either[APIError, String]] = {
    dataRepository.delete(login).map{
      case Right(deleted: String) => Right(deleted)
      case Left(error) => Left(error)
    }
  }




}
