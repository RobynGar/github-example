package models

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Json, OFormat, OWrites, Reads}

import scala.language.postfixOps


case class DeletedReturn(content: Option[String])
object DeletedReturn{
  implicit val formats: OFormat[DeletedReturn] = Json.format[DeletedReturn]
//  implicit val readObj: Reads[DeletedReturn] = (
//    (JsPath \ "content").readNullable[String] and
//    (JsPath \ "commit" \ "sha").read[String]
//  ) (DeletedReturn.apply _)
//  implicit val write: OWrites[DeletedReturn] = Json.format[DeletedReturn]
}

//case class Commit(sha: String)
//object Commit{
//  implicit val formats: OFormat[Commit] = Json.format[Commit]
//}
