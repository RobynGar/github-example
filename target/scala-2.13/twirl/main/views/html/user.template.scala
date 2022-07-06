
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
/*1.2*/import models.User

object user extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[Seq[User],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(users: Seq[User]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""
"""),format.raw/*4.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>user</title>
</head>
<body>
<h2>Github Users</h2>
<div>
    """),_display_(/*13.6*/users/*13.11*/.map/*13.15*/ { user =>_display_(Seq[Any](format.raw/*13.25*/("""
    """),format.raw/*14.5*/("""<ul>
        <li>"""),_display_(/*15.14*/user/*15.18*/.login),format.raw/*15.24*/("""</li>
        <li>"""),_display_(/*16.14*/user/*16.18*/.followers),format.raw/*16.28*/("""</li>
        <li>"""),_display_(/*17.14*/user/*17.18*/.following),format.raw/*17.28*/("""</li>
        <li>--------------------------</li>
    </ul>
    """)))}),format.raw/*20.6*/("""
"""),format.raw/*21.1*/("""</div>
</body>
</html>"""))
      }
    }
  }

  def render(users:Seq[User]): play.twirl.api.HtmlFormat.Appendable = apply(users)

  def f:((Seq[User]) => play.twirl.api.HtmlFormat.Appendable) = (users) => apply(users)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-07-06T12:28:14.782
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/user.scala.html
                  HASH: 6238a20159c0e80eb6340d6720acf5e166440073
                  MATRIX: 432->1|757->21|869->40|896->41|1061->180|1075->185|1088->189|1136->199|1168->204|1213->222|1226->226|1253->232|1299->251|1312->255|1343->265|1389->284|1402->288|1433->298|1528->363|1556->364
                  LINES: 17->1|22->2|27->3|28->4|37->13|37->13|37->13|37->13|38->14|39->15|39->15|39->15|40->16|40->16|40->16|41->17|41->17|41->17|44->20|45->21
                  -- GENERATED --
              */
          