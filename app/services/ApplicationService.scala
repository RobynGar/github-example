package services

import connectors.ApplicationConnector
import models.File.jsonReads
import models.{APIError, Content, CreateFile, DeletedReturn, FFitems, File, Repository, RequestDelete, ReturnCreatedFile, User}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.Request
import repositories.TraitDataRepo

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApplicationService @Inject()(connector: ApplicationConnector, dataRepository: TraitDataRepo)(implicit ec: ExecutionContext) {



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


  def addApiUser(login: String): Future[Either[APIError, User]]= {
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

  def getUser(login: String)(implicit ec: ExecutionContext): Future[Either[APIError, User]] = {
    connector.get[User](s"https://api.github.com/users/$login")
  }


  def getUsersRepo(login: String)(implicit ec: ExecutionContext): Future[Either[APIError, List[String]]] = {
    connector.getUserRepo[Repository](s"https://api.github.com/users/$login/repos")
  }

  def getUsersRepoInfo(login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, Repository]] = {
    connector.getUserRepoInfo[Repository](s"https://api.github.com/repos/$login/$repoName")
  }

  def getRepoContent(login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, Seq[FFitems]]] = {
    connector.getRepoContent[FFitems](s"https://api.github.com/repos/$login/$repoName/contents")
  }

  def getDirContent(urlOverride: Option[String] = None, dirName: String, login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, Seq[FFitems]]] = {
    connector.getDirContent[FFitems](s"https://api.github.com/repos/$login/$repoName/contents/$dirName")
  }

  def getFileContent(urlOverride: Option[String] = None, filePath: String, login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, File]] = {
    connector.getFileContent[File](s"https://api.github.com/repos/$login/$repoName/contents/$filePath")
  }

  def createFile(login: String, repoName: String, filePath: String, newFile: Request[JsValue])(implicit ec: ExecutionContext): Future[Either[APIError, ReturnCreatedFile]] = {
    newFile.body.validate[CreateFile] match {
      case JsSuccess(newFile, _) => connector.createFile[ReturnCreatedFile](newFile, s"https://api.github.com/repos/$login/$repoName/contents/$filePath")
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "could not validate file")))
    }
  }

  def updateFile(login: String, repoName: String, filePath: String, updatedFile: Request[JsValue])(implicit ec: ExecutionContext): Future[Either[APIError, ReturnCreatedFile]] = {
    updatedFile.body.validate[CreateFile] match {
      case JsSuccess(validatedFile, _) => getFileContent(filePath= filePath, login = login, repoName = repoName).flatMap {
        case Right(file) =>
          connector.updateFile[ReturnCreatedFile](validatedFile, s"https://api.github.com/repos/$login/$repoName/contents/$filePath", file.sha)
        case Left(value) =>
          Future(Left(APIError.BadAPIResponse(400, "could not update file")))
      }
    case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "could not validate file")))
    }
  }


  def deleteFile(login: String, repoName: String, filePath: String, deleteCommitMessage: Request[JsValue])(implicit ec: ExecutionContext): Future[Either[APIError, DeletedReturn]] = {
    getFileContent(filePath= filePath, login = login, repoName = repoName).flatMap {
      case Right(file) =>
        val deletedRequest = RequestDelete(deleteCommitMessage.body.as[String], file.sha)
//        println(Json.toJson(deletedRequest))
        connector.deleteFile[DeletedReturn](s"https://api.github.com/repos/$login/$repoName/contents/$filePath", deletedRequest)
      case Left(value) =>
        Future(Left(APIError.BadAPIResponse(404, "could not delete file")))
        }
    }

  def repoReadMe(login: String, repoName: String)(implicit ec: ExecutionContext): Future[Either[APIError, File]] = {
    connector.repoReadMe[File](s"https://api.github.com/repos/$login/$repoName/readme")
  }

  def dirReadMe(login: String, repoName: String, dir: String)(implicit ec: ExecutionContext): Future[Either[APIError, File]] = {
    connector.dirReadMe[File](s"https://api.github.com/repos/$login/$repoName/readme/$dir")
  }

  def downloadTar(login: String, repoName: String, branch: Option[String])(implicit ec: ExecutionContext): Future[Either[APIError, Int]] = {
    connector.downloadTar(s"https://api.github.com/repos/$login/$repoName/tarball/${branch.getOrElse("main")}")
  }

  def downloadZip(login: String, repoName: String, branch: String)(implicit ec: ExecutionContext): Future[Either[APIError, Int]] = {
    connector.downloadZip(s"https://api.github.com/repos/$login/$repoName/zipball/$branch")
  }

}
