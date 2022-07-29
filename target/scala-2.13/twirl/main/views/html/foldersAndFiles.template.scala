
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
            <li><a href="""),_display_(/*17.26*/controllers/*17.37*/.routes.ApplicationController.dirContent(item.path, login, repoName)),format.raw/*17.105*/("""> """),_display_(/*17.108*/item/*17.112*/.name),format.raw/*17.117*/(""" """),format.raw/*17.118*/("""</a></li>
        """)))}),format.raw/*18.10*/("""
    """)))}),format.raw/*19.6*/("""


"""),format.raw/*22.1*/("""<h2>Files</h2>

    """),_display_(/*24.6*/contents/*24.14*/.map/*24.18*/{item =>_display_(Seq[Any](format.raw/*24.26*/("""
        """),_display_(/*25.10*/if(item.fType == "file")/*25.34*/{_display_(Seq[Any](format.raw/*25.35*/("""
            """),format.raw/*26.13*/("""<h4>path: """),_display_(/*26.24*/item/*26.28*/.path),format.raw/*26.33*/("""</h4>
            <li>sha: """),_display_(/*27.23*/item/*27.27*/.sha),format.raw/*27.31*/("""</li>
            <li><a href="""),_display_(/*28.26*/controllers/*28.37*/.routes.ApplicationController.fileContent(item.path, login, repoName)),format.raw/*28.106*/("""> """),_display_(/*28.109*/item/*28.113*/.name),format.raw/*28.118*/(""" """),format.raw/*28.119*/("""</a></li>
        """)))}),format.raw/*29.10*/("""
    """)))}),format.raw/*30.6*/("""

"""),format.raw/*32.1*/("""</body>
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
                  DATE: 2022-07-27T16:04:29.667
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/foldersAndFiles.scala.html
                  HASH: f4cce33e35b9dbdf7295cf67a4864dedfeadacf4
                  MATRIX: 432->1|788->24|939->82|1108->225|1125->233|1138->237|1184->245|1221->255|1253->278|1292->279|1333->292|1371->303|1384->307|1410->312|1465->340|1478->344|1503->348|1561->379|1581->390|1671->458|1702->461|1716->465|1743->470|1773->471|1823->490|1859->496|1889->499|1936->520|1953->528|1966->532|2012->540|2049->550|2082->574|2121->575|2162->588|2200->599|2213->603|2239->608|2294->636|2307->640|2332->644|2390->675|2410->686|2501->755|2532->758|2546->762|2573->767|2603->768|2653->787|2689->793|2718->795
                  LINES: 17->1|22->2|27->3|37->13|37->13|37->13|37->13|38->14|38->14|38->14|39->15|39->15|39->15|39->15|40->16|40->16|40->16|41->17|41->17|41->17|41->17|41->17|41->17|41->17|42->18|43->19|46->22|48->24|48->24|48->24|48->24|49->25|49->25|49->25|50->26|50->26|50->26|50->26|51->27|51->27|51->27|52->28|52->28|52->28|52->28|52->28|52->28|52->28|53->29|54->30|56->32
                  -- GENERATED --
              */
          