
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Play Scala API demo")/*3.29*/ {_display_(Seq[Any](format.raw/*3.31*/("""

"""),format.raw/*5.1*/("""<div class="container h-100 d-flex justify-content-center">

  <div class="jumbotron my-auto">

    <div class="container mb-5">
      <h1 class="display-3 row">Play Scala API GitHub Demo</h1>
    </div>


    <p class="lead mb-5">This project is deployed via Heroku.</p>

    <h3>API Documentation</h3>
    <div class="justify-content-center">
      <table class="table">
        <thead>
        <tr>
          <th scope="col">URL</th>
          <th scope="col">HTTP Method</th>
          <th scope="col">Required Header</th>
          <th scope="col">Body</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <th scope="row">/github/users</th>
          <td><code>GET</code></td>
          <td>None</td>
          <td>N/A</td>
        </tr>
        <tr>
          <th scope="row">/github/users</th>
          <td><code>POST</code></td>
          <td>Content-Type: application/json</td>
          <td><code>
            "login" : String
            <br>
            "created_at" : DateTime
            <br>
            "location" : String
            <br>
            "followers" : Int
            <br>
            "following" : Int
          </code></td>
        </tr>
        <tr>
          <th scope="row">/github/users/:username</th>
          <td><code>GET</code></td>
          <td>None</td>
          <td>N/A</td>
        </tr>
        <tr>
          <th scope="row">/github/users/:username</th>
          <td><code>PUT</code></td>
          <td>Content-Type: application/json</td>
          <td><code>
            "login" : String
            <br>
            "created_at" : DateTime
            <br>
            "location" : String
            <br>
            "followers" : Int
            <br>
            "following" : Int
          </code></td>
        </tr>
        <tr>
          <th scope="row">/github/users/:username</th>
          <td><code>DELETE</code></td>
          <td>Content-Type: application/json</td>
          <td>N/A</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

</div>
""")))}),format.raw/*84.2*/("""
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-08-03T12:11:39.822
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/github-example/app/views/index.scala.html
                  HASH: fe1c7ae6f3ec5e48975e4516a572144731a98609
                  MATRIX: 722->1|818->4|845->6|880->33|919->35|947->37|3027->2087
                  LINES: 21->1|26->2|27->3|27->3|27->3|29->5|108->84
                  -- GENERATED --
              */
          