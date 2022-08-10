package stubs

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import helpers.WireMockMethods
import play.api.libs.json.{JsValue, Json}

object ApplicationConnectorStub extends WireMockMethods {

  // private val prefixUrl: String => String = url =>  s"http://localhost:11111$url"

  def stubGet(status: Int, response: JsValue, path: String): StubMapping = {
    when(method = GET, uri = path).thenReturn(status = status, body = response)
  }

  def stubGetNoBody(status: Int, path: String): StubMapping = {
    when(method = GET, uri = path).thenReturn(status = status, body = "302")
  }

  def stubPut(status: Int, response: JsValue, path: String): StubMapping = {
    when(method = PUT, uri = path).thenReturn(status = status, body = response)
  }

  def stubDelete(status: Int, response: JsValue, path: String): StubMapping = {
    when(method = DELETE, uri = path).thenReturn(status = status, body = response)
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
      "node_id" -> "MDEwOlJlcG9zaXRvcnkxMzI5MzU2NDg=",
      "name" -> "boysenberry-repo-1",
      "full_name" -> "octocat/boysenberry-repo-1",
      "private" -> false,
      "owner" -> Json.obj(
        "login" -> "octocat",
        "id" -> 583231
      )
    ),
    Json.obj(
      "id" -> 18221276,
      "node_id" -> "MDEwOlJlcG9zaXRvcnkxODIyMTI3Ng==",
      "name" -> "git-consortium",
      "full_name" -> "octocat/git-consortium",
      "private" -> false,
      "owner" -> Json.obj(
        "login" -> "octocat",
        "id" -> 583231
      )
    ),
    Json.obj(
      "id" -> 20978623,
      "node_id" -> "MDEwOlJlcG9zaXRvcnkyMDk3ODYyMw==",
      "name" -> "hello-worId",
      "full_name" -> "octocat/hello-worId",
      "private" -> false,
      "owner" -> Json.obj(
        "login" -> "octocat",
        "id" -> 583231
      )
    )
  )

  val usersReposContent: JsValue = Json.arr(
    Json.obj(
      "name" -> "README.md",
      "path" -> "README.md",
      "sha" -> "dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d",
      "size" -> 1291,
      "url" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/README.md?ref=master",
      "html_url" -> "https://github.com/octocat/boysenberry-repo-1/blob/master/README.md",
      "git_url" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d",
      "download_url" -> "https://raw.githubusercontent.com/octocat/boysenberry-repo-1/master/README.md",
      "type" -> "file",
      "_links" -> Json.obj(
        "self" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/README.md?ref=master",
        "git" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d",
        "html" -> "https://github.com/octocat/boysenberry-repo-1/blob/master/README.md"
      )
    ),
    Json.obj(
      "name" -> "READTHIS.md",
      "path" -> "READTHIS.md",
      "sha" -> "e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
      "size" -> 0,
      "url" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/READTHIS.md?ref=master",
      "html_url" -> "https://github.com/octocat/boysenberry-repo-1/blob/master/READTHIS.md",
      "git_url" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
      "download_url" -> "https://raw.githubusercontent.com/octocat/boysenberry-repo-1/master/READTHIS.md",
      "type" -> "file",
      "_links" -> Json.obj(
        "self" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/READTHIS.md?ref=master",
        "git" -> "https://api.github.com/repos/octocat/boysenberry-repo-1/git/blobs/e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
        "html" -> "https://github.com/octocat/boysenberry-repo-1/blob/master/READTHIS.md"
      )
    )
  )

  val usersRepoFile: JsValue = Json.obj(
    "name" -> "READTHIS.md",
    "sha" -> "fggtyhuyju",
    "type" -> "file",
    "path" -> "service/ApplicationService",
    "url" -> "url",
    "download_url" -> "testurl",
    "content" -> "YmFzZTY0"
  )

  val reposInfo: JsValue = Json.obj(
    "id" -> 132935648,
    "node_id" -> "MDEwOlJlcG9zaXRvcnkxMzI5MzU2NDg=",
    "name" -> "testRepo",
    "full_name" -> "octocat/testRepo",
    "private" -> false,
    "owner" -> Json.obj(
      "login" -> "octocat",
      "id" -> 583231,
      "node_id" -> "MDQ6VXNlcjU4MzIzMQ==",
      "avatar_url" -> "https://avatars.githubusercontent.com/u/583231?v=4",
      "gravatar_id" -> "",
      "url" -> "https://api.github.com/users/octocat",
      "html_url" -> "https://github.com/octocat",
      "followers_url" -> "https://api.github.com/users/octocat/followers",
      "following_url" -> "https://api.github.com/users/octocat/following{/other_user}",
      "gists_url" -> "https://api.github.com/users/octocat/gists{/gist_id}",
      "starred_url" -> "https://api.github.com/users/octocat/starred{/owner}{/repo}",
      "subscriptions_url" -> "https://api.github.com/users/octocat/subscriptions",
      "organizations_url" -> "https://api.github.com/users/octocat/orgs",
      "repos_url" -> "https://api.github.com/users/octocat/repos",
      "events_url" -> "https://api.github.com/users/octocat/events{/privacy}",
      "received_events_url" -> "https://api.github.com/users/octocat/received_events",
      "type" -> "User",
      "site_admin" -> false
    ),
    "html_url" -> "https://github.com/octocat/testRepo",
    "description" -> "Testing",
    "fork" -> true,
    "url" -> "https://api.github.com/repos/octocat/testRepo"
  )

  val gitCreateFile: JsValue = Json.obj(
    "content" -> Json.obj(
      "name" -> "hello.txt",
      "path" -> "notes/hello.txt",
      "sha" -> "95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
      "size" -> 9,
      "url" -> "https://api.github.com/repos/octocat/Hello-World/contents/notes/hello.txt",
      "html_url" -> "https://github.com/octocat/Hello-World/blob/master/notes/hello.txt",
      "git_url" -> "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
      "download_url" -> "https://raw.githubusercontent.com/octocat/HelloWorld/master/notes/hello.txt",
      "type" -> "file",
      "_links" -> Json.obj(
        "self" -> "https://api.github.com/repos/octocat/Hello-World/contents/notes/hello.txt",
        "git" -> "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
        "html" -> "https://github.com/octocat/Hello-World/blob/master/notes/hello.txt"
      )
    ),
    "commit" -> Json.obj(
      "sha" -> "7638417db6d59f3c431d3e1f261cc637155684cd",
      "node_id" -> "MDY6Q29tbWl0NzYzODQxN2RiNmQ1OWYzYzQzMWQzZTFmMjYxY2M2MzcxNTU2ODRjZA==",
      "url" -> "https://api.github.com/repos/octocat/Hello-World/git/commits/7638417db6d59f3c431d3e1f261cc637155684cd",
      "html_url" -> "https://github.com/octocat/Hello-World/git/commit/7638417db6d59f3c431d3e1f261cc637155684cd",
      "author" -> Json.obj(
        "date" -> "2014-11-07T22:01:45Z",
        "name" -> "Monalisa Octocat",
        "email" -> "octocat@github.com"
      ),
      "committer" -> Json.obj(
        "date" -> "2014-11-07T22:01:45Z",
        "name" -> "Monalisa Octocat",
        "email" -> "octocat@github.com"
      ),
      "message" -> "my commit message",
      "tree" -> Json.obj(
        "url" -> "https://api.github.com/repos/octocat/Hello-World/git/trees/691272480426f78a0138979dd3ce63b77f706feb",
        "sha" -> "691272480426f78a0138979dd3ce63b77f706feb"
      )
    )
  )

  val gitDeletedFile: JsValue = Json.obj(
    "content" -> None,
    "commit" -> Json.obj(
      "sha" -> "7638417db6d59f3c431d3e1f261cc637155684cd",
      "node_id" -> "MDY6Q29tbWl0NzYzODQxN2RiNmQ1OWYzYzQzMWQzZTFmMjYxY2M2MzcxNTU2ODRjZA==",
      "url" -> "https://api.github.com/repos/octocat/Hello-World/git/commits/7638417db6d59f3c431d3e1f261cc637155684cd",
      "html_url" -> "https://github.com/octocat/Hello-World/git/commit/7638417db6d59f3c431d3e1f261cc637155684cd",
      "author" -> Json.obj(
        "date" -> "2014-11-07T22:01:45Z",
        "name" -> "Monalisa Octocat",
        "email" -> "octocat@github.com"
      ),
      "committer" -> Json.obj(
        "date" -> "2014-11-07T22:01:45Z",
        "name" -> "Monalisa Octocat",
        "email" -> "octocat@github.com"
      ),
      "message" -> "my commit message",
      "tree" -> Json.obj(
        "url" -> "https://api.github.com/repos/octocat/Hello-World/git/trees/691272480426f78a0138979dd3ce63b77f706feb",
        "sha" -> "691272480426f78a0138979dd3ce63b77f706feb"
      ),
      "parents" -> Json.arr(
        Json.obj(
          "url" -> "https://api.github.com/repos/octocat/Hello-World/git/commits/1acc419d4d6a9ce985db7be48c6349a0475975b5",
          "html_url" -> "https://github.com/octocat/Hello-World/git/commit/1acc419d4d6a9ce985db7be48c6349a0475975b5",
          "sha" -> "1acc419d4d6a9ce985db7be48c6349a0475975b5"
        )
      ),
      "verification" -> Json.obj(
        "verified" -> false,
        "reason" -> "unsigned",
        "signature" -> None,
        "payload" -> None
      )
    )
  )


}






