
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
            <li><a href="""),_display_(/*16.26*/controllers/*16.37*/.routes.ApplicationController.dirContent(item.path, login, repoName)),format.raw/*16.105*/("""> """),_display_(/*16.108*/item/*16.112*/.name),format.raw/*16.117*/(""" """),format.raw/*16.118*/("""</a></li>
        """)))}),format.raw/*17.10*/("""
    """)))}),format.raw/*18.6*/("""


"""),format.raw/*21.1*/("""<h2>Files</h2>

    """),_display_(/*23.6*/contents/*23.14*/.map/*23.18*/{item =>_display_(Seq[Any](format.raw/*23.26*/("""
        """),_display_(/*24.10*/if(item.fType == "file")/*24.34*/{_display_(Seq[Any](format.raw/*24.35*/("""
            """),format.raw/*25.13*/("""<h4>path: """),_display_(/*25.24*/item/*25.28*/.path),format.raw/*25.33*/("""</h4>
            <li><a href="""),_display_(/*26.26*/controllers/*26.37*/.routes.ApplicationController.fileContent(item.path, login, repoName)),format.raw/*26.106*/("""> """),_display_(/*26.109*/item/*26.113*/.name),format.raw/*26.118*/(""" """),format.raw/*26.119*/("""</a></li>
        """)))}),format.raw/*27.10*/("""
    """)))}),format.raw/*28.6*/("""

"""),format.raw/*30.1*/("""</body>
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
                  DATE: 2022-07-12T12:22:14.280
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/foldersAndFiles.scala.html
                  HASH: 60c08b3e89f8af92f9ca3c0aab7a026c03a8bdfc
                  MATRIX: 432->1|788->24|939->82|1108->225|1125->233|1138->237|1184->245|1221->255|1253->278|1292->279|1333->292|1371->303|1384->307|1410->312|1468->343|1488->354|1578->422|1609->425|1623->429|1650->434|1680->435|1730->454|1766->460|1796->463|1843->484|1860->492|1873->496|1919->504|1956->514|1989->538|2028->539|2069->552|2107->563|2120->567|2146->572|2204->603|2224->614|2315->683|2346->686|2360->690|2387->695|2417->696|2467->715|2503->721|2532->723
                  LINES: 17->1|22->2|27->3|37->13|37->13|37->13|37->13|38->14|38->14|38->14|39->15|39->15|39->15|39->15|40->16|40->16|40->16|40->16|40->16|40->16|40->16|41->17|42->18|45->21|47->23|47->23|47->23|47->23|48->24|48->24|48->24|49->25|49->25|49->25|49->25|50->26|50->26|50->26|50->26|50->26|50->26|50->26|51->27|52->28|54->30
                  -- GENERATED --
              */
          