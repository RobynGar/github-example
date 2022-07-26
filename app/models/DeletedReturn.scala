package models

import play.api.libs.json.{Json, OFormat}


case class DeletedReturn(content: String, commit: Commit)
object DeletedReturn{
  implicit val formats: OFormat[DeletedReturn] = Json.format[DeletedReturn]
}

case class Commit(sha: String)
object Commit{
  implicit val formats: OFormat[Commit] = Json.format[Commit]
}
