package connectors

import baseSpec.BaseSpecWithApplication
import models.User
import play.api.libs.json.OFormat


class ApplicationConnectorSpec extends BaseSpecWithApplication{

  val testApplicationConnector: ApplicationConnector = new ApplicationConnector(ws)

  private val user: User = User(
    "RobynGar",
    "2021-07-01T11:04:15Z",
    None,
    7,
    0
  )

  "ApplicationConnector .get()" should {
    val url = "https://api.github.com/users/RobynGar"
    "return a user from the api" in {
      val getResult = testApplicationConnector.get(url)(OFormat[User], executionContext)

     whenReady(getResult) {result =>
       result shouldBe Right(user)
     }

    }
  }

}
