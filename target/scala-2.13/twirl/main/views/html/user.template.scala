
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

object user extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[User,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(user: User):play.twirl.api.HtmlFormat.Appendable = {
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
<h2>Github User Page</h2>
<div>

    <ul>
        <h3>"""),_display_(/*15.14*/user/*15.18*/.login),format.raw/*15.24*/("""</h3>
        <li>"""),_display_(/*16.14*/user/*16.18*/.created_at),format.raw/*16.29*/("""</li>
        <li>"""),_display_(/*17.14*/user/*17.18*/.location.getOrElse("unknown")),format.raw/*17.48*/("""</li>
        <li>"""),_display_(/*18.14*/user/*18.18*/.followers),format.raw/*18.28*/("""</li>
        <li>"""),_display_(/*19.14*/user/*19.18*/.following),format.raw/*19.28*/("""</li>
        <button href=""""),_display_(/*20.24*/controllers/*20.35*/.routes.ApplicationController.usersRepos(user.login)),format.raw/*20.87*/("""">Repositories</button>

    </ul>

</div>
</body>
</html>"""))
      }
    }
  }

  def render(user:User): play.twirl.api.HtmlFormat.Appendable = apply(user)

  def f:((User) => play.twirl.api.HtmlFormat.Appendable) = (user) => apply(user)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-07-07T15:02:38.665
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/user.scala.html
                  HASH: 423c1bf13b4ad896186fc15509d3253752e1c84a
                  MATRIX: 432->1|752->21|858->34|885->35|1073->196|1086->200|1113->206|1159->225|1172->229|1204->240|1250->259|1263->263|1314->293|1360->312|1373->316|1404->326|1450->345|1463->349|1494->359|1550->388|1570->399|1643->451
                  LINES: 17->1|22->2|27->3|28->4|39->15|39->15|39->15|40->16|40->16|40->16|41->17|41->17|41->17|42->18|42->18|42->18|43->19|43->19|43->19|44->20|44->20|44->20
                  -- GENERATED --
              */
          