package stubs

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import helpers.WireMockMethods
import play.api.libs.json.{JsValue, Json}

object ApplicationConnectorStub extends WireMockMethods{

 // private val prefixUrl: String => String = url =>  s"http://localhost:11111$url"

  def stubGetUser(status: Int, response: JsValue, path: String): StubMapping = {
    when(method = GET, uri = path).thenReturn(status = status, body = response)
  }

  val responseUser: JsValue = Json.obj(
    "login" -> "octocat",
    "created_at" -> "2011-01-25T18:44:36Z",
    "location" -> Some("San Francisco"),
    "followers" -> 6536,
    "following" -> 9
  )

  val errorJson: JsValue = Json.obj(
    "message" -> "Not Found",
    "documentation_url" -> "https://docs.github.com/rest/reference/repos#list-repositories-for-a-user"
  )

  val usersRepos: JsValue = Json.arr(
    Json.obj(
      "id" -> 132935648,
      "node_id"-> "MDEwOlJlcG9zaXRvcnkxMzI5MzU2NDg=",
      "name"-> "boysenberry-repo-1",
      "full_name"-> "octocat/boysenberry-repo-1",
      "private"-> false,
      "owner"-> Json.obj(
        "login"-> "octocat",
        "id"-> 583231
      )
    ),
    Json.obj(
      "id"-> 18221276,
      "node_id"-> "MDEwOlJlcG9zaXRvcnkxODIyMTI3Ng==",
      "name"-> "git-consortium",
      "full_name"-> "octocat/git-consortium",
      "private"-> false,
      "owner"-> Json.obj(
        "login"-> "octocat",
        "id"-> 583231
      )
    ),
    Json.obj(
      "id"-> 20978623,
      "node_id"-> "MDEwOlJlcG9zaXRvcnkyMDk3ODYyMw==",
      "name"-> "hello-worId",
      "full_name"-> "octocat/hello-worId",
      "private"-> false,
      "owner"-> Json.obj(
        "login"-> "octocat",
        "id"-> 583231
      )
    )
  )

  val usersReposContent: JsValue = Json.arr(
    Json.obj(
      "name"-> "README.md",
      "path"-> "README.md",
      "sha"-> "dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d",
      "size"-> 1291,
      "url"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/README.md?ref=master",
      "html_url"-> "https://github.com/octocat/boysenberry-repo-1/blob/master/README.md",
      "git_url"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d",
      "download_url"-> "https://raw.githubusercontent.com/octocat/boysenberry-repo-1/master/README.md",
      "type"-> "file",
      "_links"-> Json.obj(
        "self"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/README.md?ref=master",
        "git"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d",
        "html"-> "https://github.com/octocat/boysenberry-repo-1/blob/master/README.md"
      )
    ),
    Json.obj(
        "name"-> "READTHIS.md",
        "path"-> "READTHIS.md",
        "sha"-> "e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
        "size"-> 0,
        "url"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/READTHIS.md?ref=master",
        "html_url"-> "https://github.com/octocat/boysenberry-repo-1/blob/master/READTHIS.md",
        "git_url"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
        "download_url"-> "https://raw.githubusercontent.com/octocat/boysenberry-repo-1/master/READTHIS.md",
        "type"-> "file",
        "_links"-> Json.obj(
          "self"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/READTHIS.md?ref=master",
          "git"-> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
          "html"-> "https://github.com/octocat/boysenberry-repo-1/blob/master/READTHIS.md"
        )
    )
  )

  val usersRepoFile: JsValue = Json.obj(
    "name"-> "READTHIS.md",
    "sha"-> "fggtyhuyju",
    "type" -> "file",
    "path"->"service/ApplicationService",
    "url"->"url",
    "download_url"->"testurl",
    "content"->"YmFzZTY0"
  )

}
