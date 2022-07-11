package models

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Json, OFormat, Reads, Writes}

case class FFitems(name: String, fType: String, path: String, url: String)


object FFitems{
 // implicit val formats: OFormat[FFitems] = Json.format[FFitems]
  implicit val jsonReads: Reads[FFitems] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "type").read[String] and
      (JsPath \ "path").read[String] and
      (JsPath \ "url").read[String]
  )(FFitems.apply _)
}