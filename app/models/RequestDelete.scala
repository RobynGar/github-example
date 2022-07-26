package models

import play.api.libs.json.{Json, OFormat}

case class RequestDelete(message: String, sha: String)

object RequestDelete{
  implicit val formats: OFormat[RequestDelete] = Json.format[RequestDelete]
}