
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


<h3>Folders</h3>
    """),_display_(/*13.6*/contents/*13.14*/.map/*13.18*/{item =>_display_(Seq[Any](format.raw/*13.26*/("""
        """),_display_(/*14.10*/if(item.fType == "dir")/*14.33*/{_display_(Seq[Any](format.raw/*14.34*/("""
            """),format.raw/*15.13*/("""<h5>path: """),_display_(/*15.24*/item/*15.28*/.path),format.raw/*15.33*/("""</h5>
            <li><a href="""),_display_(/*16.26*/controllers/*16.37*/.routes.ApplicationController.dirContent(item.path, login, repoName)),format.raw/*16.105*/("""> """),_display_(/*16.108*/item/*16.112*/.name),format.raw/*16.117*/(""" """),format.raw/*16.118*/("""</a></li>
        """)))}),format.raw/*17.10*/("""
    """)))}),format.raw/*18.6*/("""
"""),format.raw/*19.1*/("""<h3>Files</h3>

    """),_display_(/*21.6*/contents/*21.14*/.map/*21.18*/{item =>_display_(Seq[Any](format.raw/*21.26*/("""
        """),_display_(/*22.10*/if(item.fType == "file")/*22.34*/{_display_(Seq[Any](format.raw/*22.35*/("""
            """),format.raw/*23.13*/("""<h4>path: """),_display_(/*23.24*/item/*23.28*/.path),format.raw/*23.33*/("""</h4>
            <li> """),_display_(/*24.19*/item/*24.23*/.name),format.raw/*24.28*/(""" """),format.raw/*24.29*/("""</li>
        """)))}),format.raw/*25.10*/("""
    """)))}),format.raw/*26.6*/("""
"""),format.raw/*27.1*/("""</body>
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
                  DATE: 2022-07-11T15:21:53.780
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/app/views/foldersAndFiles.scala.html
                  HASH: 94afac6dd7c325c0a0b05322cee98e3b0341e09c
                  MATRIX: 432->1|788->24|939->82|1108->225|1125->233|1138->237|1184->245|1221->255|1253->278|1292->279|1333->292|1371->303|1384->307|1410->312|1468->343|1488->354|1578->422|1609->425|1623->429|1650->434|1680->435|1730->454|1766->460|1794->461|1841->482|1858->490|1871->494|1917->502|1954->512|1987->536|2026->537|2067->550|2105->561|2118->565|2144->570|2195->594|2208->598|2234->603|2263->604|2309->619|2345->625|2373->626
                  LINES: 17->1|22->2|27->3|37->13|37->13|37->13|37->13|38->14|38->14|38->14|39->15|39->15|39->15|39->15|40->16|40->16|40->16|40->16|40->16|40->16|40->16|41->17|42->18|43->19|45->21|45->21|45->21|45->21|46->22|46->22|46->22|47->23|47->23|47->23|47->23|48->24|48->24|48->24|48->24|49->25|50->26|51->27
                  -- GENERATED --
              */
          