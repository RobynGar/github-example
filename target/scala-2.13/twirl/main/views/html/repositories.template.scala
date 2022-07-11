
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

object repositories extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[List[String],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(repo: List[String]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""
"""),format.raw/*4.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>repos</title>
</head>
<body>
<h3>"""),_display_(/*11.6*/repo/*11.10*/.head),format.raw/*11.15*/("""'s Repositories</h3>
<ul>
  """),_display_(/*13.4*/repo/*13.8*/.tail.map/*13.17*/{name =>_display_(Seq[Any](format.raw/*13.25*/("""
    """),format.raw/*14.5*/("""<li><a href="""),_display_(/*14.18*/controllers/*14.29*/.routes.ApplicationController.usersRepoInfo(repo.head, name)),format.raw/*14.89*/("""> """),_display_(/*14.92*/name),format.raw/*14.96*/(""" """),format.raw/*14.97*/("""</a></li>
      """)))}),format.raw/*15.8*/("""

"""),format.raw/*17.1*/("""</ul>

</body>
</html>"""))
      }
    }
  }

  def render(repo:List[String]): play.twirl.api.HtmlFormat.Appendable = apply(repo)

  def f:((List[String]) => play.twirl.api.HtmlFormat.Appendable) = (repo) => apply(repo)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-07-08T12:28:14.098
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/repositories.scala.html
                  HASH: 7e99207baa9f06a57541d495e216721566a467ac
                  MATRIX: 742->2|856->23|883->24|1021->136|1034->140|1060->145|1115->174|1127->178|1145->187|1191->195|1223->200|1263->213|1283->224|1364->284|1394->287|1419->291|1448->292|1495->309|1524->311
                  LINES: 21->2|26->3|27->4|34->11|34->11|34->11|36->13|36->13|36->13|36->13|37->14|37->14|37->14|37->14|37->14|37->14|37->14|38->15|40->17
                  -- GENERATED --
              */
          