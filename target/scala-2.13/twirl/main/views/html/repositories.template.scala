
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
/*1.2*/import models.Repository

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
                  DATE: 2022-07-08T11:58:15.817
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/repositories.scala.html
                  HASH: f0d88cf245e3e518d1c0d4bf04f462454fad648c
                  MATRIX: 432->1|774->27|888->48|915->49|1053->161|1066->165|1092->170|1147->199|1159->203|1177->212|1223->220|1255->225|1295->238|1315->249|1396->309|1426->312|1451->316|1480->317|1527->334|1556->336
                  LINES: 17->1|22->2|27->3|28->4|35->11|35->11|35->11|37->13|37->13|37->13|37->13|38->14|38->14|38->14|38->14|38->14|38->14|38->14|39->15|41->17
                  -- GENERATED --
              */
          