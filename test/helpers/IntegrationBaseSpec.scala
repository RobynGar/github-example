package helpers

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.test.Injecting
import play.api.{Application, Environment, Mode}

import scala.concurrent.ExecutionContext


trait IntegrationBaseSpec extends AnyWordSpecLike with Matchers with ScalaFutures with WireMockHelper with
  GuiceOneServerPerSuite with BeforeAndAfterEach with BeforeAndAfterAll with Injecting {

  val mockHost: String = WireMockHelper.host
  val mockPort: String = WireMockHelper.wireMockPort.toString



  override lazy val client: WSClient = app.injector.instanceOf[WSClient]
  implicit val ec: ExecutionContext = app.injector.instanceOf[ExecutionContext]



  override implicit lazy val app: Application = new GuiceApplicationBuilder()
    .in(Environment.simple(mode = Mode.Test)) //maybe add config, this was .Dev but i got a warning about filters headers which goes after i change it to test
    .build()

  override def beforeAll(): Unit = {
    super.beforeAll()
    startWireMock()
  }

  override def afterAll(): Unit = {
    stopWireMock()
    super.afterAll()
  }

  def buildRequest(path: String): WSRequest = {
    client.url(s"http://$mockHost:$mockPort$path")
      .withFollowRedirects(false)
  }


}
