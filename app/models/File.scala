package models


import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Json, OWrites, Reads}

case class File(name: String, sha: String, fType: String, path: String, url: String, download_url: String, content: String, decodedContent: String)

object File{
  implicit val jsonReads: Reads[File] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "sha").read[String] and
      (JsPath \ "type").read[String] and
      (JsPath \ "path").read[String] and
      (JsPath \ "url").read[String] and
      (JsPath \ "download_url").read[String] and
      (JsPath \ "content").read[String] and
      (JsPath \ "content").read[String]
    )(File.apply _)

  implicit val write: OWrites[File] = Json.format[File]

}
