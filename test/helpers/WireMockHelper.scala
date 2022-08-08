package helpers

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import org.scalatest.concurrent.{Eventually, IntegrationPatience}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.ws.WSClient

object WireMockHelper extends Eventually with IntegrationPatience {

  val wireMockPort: Int = 11111
  val host: String = "localhost"
}

trait WireMockHelper {

  self: GuiceOneServerPerSuite =>

  import WireMockHelper._

  lazy val client: WSClient = app.injector.instanceOf[WSClient]
  lazy val wireMockConf: WireMockConfiguration = wireMockConfig.port(wireMockPort)
  lazy val wireMockServer: WireMockServer = new WireMockServer(wireMockConf)

  def startWireMock(): Unit = {
    wireMockServer.start()
    WireMock.configureFor(host, wireMockPort)
  }

  def stopWireMock(): Unit = wireMockServer.stop()

  def resetWireMock(): Unit = WireMock.reset()

}
