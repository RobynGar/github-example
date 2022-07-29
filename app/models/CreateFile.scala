package models

import play.api.libs.json.{Json, OFormat}


//case class Committer(name: String, email: String)
case class CreateFile(message: String, content: String)

object CreateFile{
  implicit val formats: OFormat[CreateFile] = Json.format[CreateFile]
}
//object Committer{
//  implicit val formats: OFormat[Committer] = Json.format[Committer]
//}
