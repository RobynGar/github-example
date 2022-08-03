
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
/*1.2*/import models.FFitems

object foldersAndFiles extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Seq[FFitems],String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(contents: Seq[FFitems], login: String, repoName: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>folders and files</title>
</head>
<body>


<h2>Folders</h2>
    """),_display_(/*13.6*/contents/*13.14*/.map/*13.18*/{item =>_display_(Seq[Any](format.raw/*13.26*/("""
        """),_display_(/*14.10*/if(item.fType == "dir")/*14.33*/{_display_(Seq[Any](format.raw/*14.34*/("""
            """),format.raw/*15.13*/("""<h4>path: """),_display_(/*15.24*/item/*15.28*/.path),format.raw/*15.33*/("""</h4>
            <li>sha: """),_display_(/*16.23*/item/*16.27*/.sha),format.raw/*16.31*/("""</li>
            <li>url: """),_display_(/*17.23*/item/*17.27*/.url),format.raw/*17.31*/("""</li>
            <li><a href="""),_display_(/*18.26*/controllers/*18.37*/.routes.ApplicationController.dirContent(item.path, login, repoName)),format.raw/*18.105*/("""> """),_display_(/*18.108*/item/*18.112*/.name),format.raw/*18.117*/(""" """),format.raw/*18.118*/("""</a></li>
        """)))}),format.raw/*19.10*/("""
    """)))}),format.raw/*20.6*/("""


"""),format.raw/*23.1*/("""<h2>Files</h2>

    """),_display_(/*25.6*/contents/*25.14*/.map/*25.18*/{item =>_display_(Seq[Any](format.raw/*25.26*/("""
        """),_display_(/*26.10*/if(item.fType == "file")/*26.34*/{_display_(Seq[Any](format.raw/*26.35*/("""
            """),format.raw/*27.13*/("""<h4>path: """),_display_(/*27.24*/item/*27.28*/.path),format.raw/*27.33*/("""</h4>
            <li>sha: """),_display_(/*28.23*/item/*28.27*/.sha),format.raw/*28.31*/("""</li>
            <li>url: """),_display_(/*29.23*/item/*29.27*/.url),format.raw/*29.31*/("""</li>
            <li><a href="""),_display_(/*30.26*/controllers/*30.37*/.routes.ApplicationController.fileContent(item.path, login, repoName)),format.raw/*30.106*/("""> """),_display_(/*30.109*/item/*30.113*/.name),format.raw/*30.118*/(""" """),format.raw/*30.119*/("""</a></li>
        """)))}),format.raw/*31.10*/("""
    """)))}),format.raw/*32.6*/("""

"""),format.raw/*34.1*/("""</body>
</html>"""))
      }
    }
  }

  def render(contents:Seq[FFitems],login:String,repoName:String): play.twirl.api.HtmlFormat.Appendable = apply(contents,login,repoName)

  def f:((Seq[FFitems],String,String) => play.twirl.api.HtmlFormat.Appendable) = (contents,login,repoName) => apply(contents,login,repoName)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-08-03T12:39:57.720
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/github-example/app/views/foldersAndFiles.scala.html
                  HASH: abd94cf9d0b2382e8885ac3d4ddf583f6629e972
                  MATRIX: 432->1|788->24|939->82|1108->225|1125->233|1138->237|1184->245|1221->255|1253->278|1292->279|1333->292|1371->303|1384->307|1410->312|1465->340|1478->344|1503->348|1558->376|1571->380|1596->384|1654->415|1674->426|1764->494|1795->497|1809->501|1836->506|1866->507|1916->526|1952->532|1982->535|2029->556|2046->564|2059->568|2105->576|2142->586|2175->610|2214->611|2255->624|2293->635|2306->639|2332->644|2387->672|2400->676|2425->680|2480->708|2493->712|2518->716|2576->747|2596->758|2687->827|2718->830|2732->834|2759->839|2789->840|2839->859|2875->865|2904->867
                  LINES: 17->1|22->2|27->3|37->13|37->13|37->13|37->13|38->14|38->14|38->14|39->15|39->15|39->15|39->15|40->16|40->16|40->16|41->17|41->17|41->17|42->18|42->18|42->18|42->18|42->18|42->18|42->18|43->19|44->20|47->23|49->25|49->25|49->25|49->25|50->26|50->26|50->26|51->27|51->27|51->27|51->27|52->28|52->28|52->28|53->29|53->29|53->29|54->30|54->30|54->30|54->30|54->30|54->30|54->30|55->31|56->32|58->34
                  -- GENERATED --
              */
          