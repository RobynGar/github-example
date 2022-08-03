package connectors


import baseSpec.BaseSpecWithApplication

//import com.github.tomakehurst.wiremock.WireMockServer
//import com.github.tomakehurst.wiremock.client.WireMock
//import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
//import com.github.tomakehurst.wiremock.junit._
//import com.github.tomakehurst.wiremock.stubbing.StubMapping
//import com.github.tomakehurst.wiremock.client.WireMock._
//import com.github.tomakehurst.wiremock.stubbing.Scenario
import models.User
import play.api.libs.json.Json




class UnitApplicationConnectorSpec extends BaseSpecWithApplication{
//  private val port = 9000
//  private val hostName = "localhost"
//  private val wireMockServer = new WireMockServer(wireMockConfig().port(port)) //i think this is the mock api
//
// // val testApplicationController: ApplicationConnector = new ApplicationConnector(wireMockServer)
//
////from hmrc github but do not have HeaderCarrier that is required
//  private trait Test {
//    def setupStubs(): StubMapping
//    val connector: ApplicationConnector= app.injector.instanceOf[ApplicationConnector]
//  }
//
//
//
//
//  override def beforeEach = {
//    wireMockServer.start()
//    WireMock.configureFor(hostName, port)
//  }
//
//  override def afterEach() = {
//    wireMockServer.stop()
//  }
//
//  private val user: User = User(
//    "test",
//    "now",
//    Some("test location"),
//    2,
//    0
//  )
//
//  "WireMock" should {
//    val path = "/github/users/api/test"
//    "stub get request" in {
//
//        stubFor(
//          get(urlEqualTo(path))
//          .willReturn(
//            aResponse()
//              .withHeader("Content-Type", "application/json")
//              .withBody(user.toString.getBytes())
//              .withStatus(200)
//          )
//        )
//
//
//
//    }
//  }
//
//
//


}
