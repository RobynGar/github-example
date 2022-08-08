package stubs

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import helpers.WireMockMethods
import play.api.libs.json.{JsValue, Json}

object ApplicationConnectorStub extends WireMockMethods{

 // private val prefixUrl: String => String = url =>  s"http://localhost:11111$url"

  def stubGetUser(status: Int, fakeUser: JsValue, path: String): StubMapping = {
    when(method = GET, uri = path).thenReturn(status = status, body = fakeUser)
  }

  val responseUser = Json.obj(
    "login" -> "octocat",
    "created_at" -> "2011-01-25T18:44:36Z",
    "location" -> Some("San Francisco"),
    "followers" -> 6536,
    "following" -> 9
  )

  val errorJson = Json.obj(
    "message" -> "Not Found",
    "documentation_url" -> "https://docs.github.com/rest/reference/repos#list-repositories-for-a-user"
  )
}
