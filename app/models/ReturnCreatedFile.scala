package models

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Json, OFormat, OWrites, Reads}

case class Content(name: String, path: String, `type`: String)
case class ReturnCreatedFile(content: Content)
object Content {
//  implicit val jsonReads: Reads[Content] = (
//    (JsPath \ "name").read[String] and
//      (JsPath \ "path").read[String] and
//      (JsPath \ "type").read[String] and
//      (JsPath \ "sha").read[String]
//    ) (Content.apply _)
//
//  implicit val write: OWrites[Content] = Json.format[Content]
implicit val formats: OFormat[Content] = Json.format[Content]

}
object ReturnCreatedFile{
  implicit val formats: OFormat[ReturnCreatedFile] = Json.format[ReturnCreatedFile]
}
