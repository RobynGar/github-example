package models

import play.api.libs.json.{Json, OFormat}


case class Repository(ownersName: String, repoName: String, privateStatus: Boolean, repoURL: String, html_url: String)

object Repository{
  implicit val formats: OFormat[Repository] = Json.format[Repository]
}


