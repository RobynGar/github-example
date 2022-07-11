
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
/*2.2*/import models.Repository

object repoInfo extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[Repository,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*3.2*/(repo: Repository):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*4.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RepoInfo</title>
</head>
<body>
    <h3>"""),_display_(/*11.10*/repo/*11.14*/.repoName),format.raw/*11.23*/("""</h3>
    <ul>
        <li>Owner: """),_display_(/*13.21*/repo/*13.25*/.ownersName),format.raw/*13.36*/("""</li>
        <li>Private: """),_display_(/*14.23*/repo/*14.27*/.privateStatus),format.raw/*14.41*/("""</li>
        <li>url: """),_display_(/*15.19*/repo/*15.23*/.repoURL),format.raw/*15.31*/("""</li>
        <li>HTML-url: """),_display_(/*16.24*/repo/*16.28*/.html_url),format.raw/*16.37*/("""</li>
        <li><a href= """),_display_(/*17.23*/controllers/*17.34*/.routes.ApplicationController.repoContent(repo.ownersName,repo.repoName)),format.raw/*17.106*/(""">Folders and Files</a></li>

    </ul>
</body>
</html>"""))
      }
    }
  }

  def render(repo:Repository): play.twirl.api.HtmlFormat.Appendable = apply(repo)

  def f:((Repository) => play.twirl.api.HtmlFormat.Appendable) = (repo) => apply(repo)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-07-11T11:12:17.227
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/repoInfo.scala.html
                  HASH: 85202095063f1e59a33df1be54f4c563f9018ff8
                  MATRIX: 432->2|768->28|880->47|1026->166|1039->170|1069->179|1131->214|1144->218|1176->229|1231->257|1244->261|1279->275|1330->299|1343->303|1372->311|1428->340|1441->344|1471->353|1526->381|1546->392|1640->464
                  LINES: 17->2|22->3|27->4|34->11|34->11|34->11|36->13|36->13|36->13|37->14|37->14|37->14|38->15|38->15|38->15|39->16|39->16|39->16|40->17|40->17|40->17
                  -- GENERATED --
              */
          