package models
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
//import org.joda.time.{DateTime, _}
import play.api.libs.json.{Json, OFormat}


case class User(login: String, created_at: String, location: Option[String], followers: Int, following: Int)

object User {
  implicit val formats: OFormat[User] = Json.format[User]

}