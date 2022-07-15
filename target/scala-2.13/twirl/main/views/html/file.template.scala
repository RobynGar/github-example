
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
/*1.2*/import models.File

object file extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[File,String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(fileContents: File, login: String, repoName: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>file</title>
</head>
<body>
    <h3>"""),_display_(/*10.10*/fileContents/*10.22*/.name),format.raw/*10.27*/("""</h3>
    <ul>
        <li>path: """),_display_(/*12.20*/fileContents/*12.32*/.path),format.raw/*12.37*/("""</li>
        <li>type: """),_display_(/*13.20*/fileContents/*13.32*/.fType),format.raw/*13.38*/("""</li>
        <li>url: """),_display_(/*14.19*/fileContents/*14.31*/.url),format.raw/*14.35*/("""</li>
        <li><a href="""),_display_(/*15.22*/fileContents/*15.34*/.download_url),format.raw/*15.47*/("""> view files contents </a></li>

    </ul>

<h4>"""),_display_(/*19.6*/fileContents/*19.18*/.name),format.raw/*19.23*/(""" """),format.raw/*19.24*/("""Content's:</h4>
<p>"""),_display_(/*20.5*/fileContents/*20.17*/.decodedContent),format.raw/*20.32*/("""</p>


</body>
</html>"""))
      }
    }
  }

  def render(fileContents:File,login:String,repoName:String): play.twirl.api.HtmlFormat.Appendable = apply(fileContents,login,repoName)

  def f:((File,String,String) => play.twirl.api.HtmlFormat.Appendable) = (fileContents,login,repoName) => apply(fileContents,login,repoName)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-07-12T15:56:21.317
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/file.scala.html
                  HASH: b76747e44ab52be5317644f82ccbe3f552dd4dd4
                  MATRIX: 432->1|766->21|913->75|1055->190|1076->202|1102->207|1163->241|1184->253|1210->258|1262->283|1283->295|1310->301|1361->325|1382->337|1407->341|1461->368|1482->380|1516->393|1591->442|1612->454|1638->459|1667->460|1713->480|1734->492|1770->507
                  LINES: 17->1|22->2|27->3|34->10|34->10|34->10|36->12|36->12|36->12|37->13|37->13|37->13|38->14|38->14|38->14|39->15|39->15|39->15|43->19|43->19|43->19|43->19|44->20|44->20|44->20
                  -- GENERATED --
              */
          