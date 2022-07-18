package repositories

import com.google.inject.ImplementedBy
import models.{APIError, User}
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.{empty, equal}
import org.mongodb.scala.model.{Filters, FindOneAndUpdateOptions, IndexModel, IndexOptions, Indexes, ReplaceOptions, ReturnDocument}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[DataRepository])
trait TraitDataRepo{
  def index(): Future[Either[APIError, Seq[User]]]
  def read(login: String): Future[Either[APIError, User]]
  def create(user: User): Future[Either[APIError, User]]
  def update(login: String, newUser: User): Future[Either[APIError, User]]
  def delete(login: String): Future[Either[APIError, String]]
}

@Singleton
class DataRepository @Inject()(mongoComponent: MongoComponent)(implicit ec: ExecutionContext) extends PlayMongoRepository[User](
  collectionName = "users",
  mongoComponent = mongoComponent,
  domainFormat = User.formats,
  indexes = Seq(IndexModel(
    Indexes.ascending("login"), IndexOptions().unique(true)
  ))) with TraitDataRepo {


  private def byLogin(login: String): Bson =
    Filters.and(
      Filters.equal("login", login)
    )

  def index(): Future[Either[APIError, Seq[User]]] = {
    collection.find().toFuture().map {
      case user: Seq[User] => Right(user)
      case _ => Left(APIError.BadAPIResponse(400, "could not find any users"))
    }
  }

  def read(login: String): Future[Either[APIError, User]] = {
    collection.find(byLogin(login)).headOption flatMap {
      case Some(user) =>
        Future(Right(user))
      case _ => Future(Left(APIError.BadAPIResponse(400, "could not find any users")))
    }
  }

  def create(user: User): Future[Either[APIError, User]] = {
    val addUser = User(user.login.toLowerCase, user.created_at, user.location, user.followers, user.following)
    collection.insertOne(addUser).toFutureOption().map {
      case Some(value) if value.wasAcknowledged() => Right(user)
      case _ => Left(APIError.BadAPIResponse(400, "could not add user"))
    }
  }

  def update(login: String, newUser: User): Future[Either[APIError, User]] = {
    collection.replaceOne(
      filter = byLogin(login),
      replacement = newUser,
      options = new ReplaceOptions().upsert(true)
    ).toFutureOption().map {
      case Some(value) if value.wasAcknowledged() => Right(newUser)
      case _ => Left(APIError.BadAPIResponse(400, "could not update book"))

    }
  }


  def delete(login: String): Future[Either[APIError, String]] = {
    collection.deleteOne(
      filter= byLogin(login)).toFutureOption().map {
      case Some(value) if value.getDeletedCount == 1 => Right("deleted")
      case _ => Left(APIError.BadAPIResponse(400, "could not delete user"))
    }
  }

  def deleteAll(): Future[Unit] = collection.deleteMany(empty()).toFuture().map(_ => ())

}
