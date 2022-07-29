// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/conf/routes
// @DATE:Fri Jul 29 10:19:19 BST 2022

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:2
  HomeController_2: controllers.HomeController,
  // @LINE:5
  ApplicationController_0: controllers.ApplicationController,
  // @LINE:35
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:2
    HomeController_2: controllers.HomeController,
    // @LINE:5
    ApplicationController_0: controllers.ApplicationController,
    // @LINE:35
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_2, ApplicationController_0, Assets_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_2, ApplicationController_0, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users""", """controllers.ApplicationController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.read(login:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/create""", """controllers.ApplicationController.create()"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.update(login:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.delete(login:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/api/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.readFromAPI(login:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/add/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.addFromAPI(login:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/userspage/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.showUser(login:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos""", """controllers.ApplicationController.usersRepos(login:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/""" + "$" + """repoName<[^/]+>""", """controllers.ApplicationController.usersRepoInfo(login:String, repoName:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>""", """controllers.ApplicationController.repoContent(login:String, repoName:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/""" + "$" + """dirName<[^/]+>""", """controllers.ApplicationController.dirContent(dirName:String, login:String, repoName:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/""" + "$" + """filePath<[^/]+>""", """controllers.ApplicationController.fileContent(filePath:String, login:String, repoName:String)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/create/""" + "$" + """filePath<[^/]+>""", """controllers.ApplicationController.createFile(login:String, repoName:String, filePath:String)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/update/""" + "$" + """filePath<[^/]+>""", """controllers.ApplicationController.updateFile(login:String, repoName:String, filePath:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/delete/""" + "$" + """filePath<[^/]+>""", """controllers.ApplicationController.deleteFile(login:String, repoName:String, filePath:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/get/readme""", """controllers.ApplicationController.repoReadMe(login:String, repoName:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/get/readme/""" + "$" + """dir<[^/]+>""", """controllers.ApplicationController.dirReadMe(login:String, repoName:String, dir:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/download/tarball""", """controllers.ApplicationController.downloadTar(login:String, repoName:String, branch:Option[String])"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/download/zipball""", """controllers.ApplicationController.downloadZip(login:String, repoName:String, branch:String ?= "main")"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:2
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_2.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:5
  private[this] lazy val controllers_ApplicationController_index1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users")))
  )
  private[this] lazy val controllers_ApplicationController_index1_invoker = createInvoker(
    ApplicationController_0.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "index",
      Nil,
      "GET",
      this.prefix + """github/users""",
      """crud methods for mongodb of github users""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_ApplicationController_read2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_read2_invoker = createInvoker(
    ApplicationController_0.read(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "read",
      Seq(classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_ApplicationController_create3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/create")))
  )
  private[this] lazy val controllers_ApplicationController_create3_invoker = createInvoker(
    ApplicationController_0.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "create",
      Nil,
      "POST",
      this.prefix + """github/users/create""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_ApplicationController_update4_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_update4_invoker = createInvoker(
    ApplicationController_0.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_ApplicationController_delete5_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_delete5_invoker = createInvoker(
    ApplicationController_0.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_ApplicationController_readFromAPI6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/api/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_readFromAPI6_invoker = createInvoker(
    ApplicationController_0.readFromAPI(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "readFromAPI",
      Seq(classOf[String]),
      "GET",
      this.prefix + """github/users/api/""" + "$" + """login<[^/]+>""",
      """showing and adding to mongodb from github api. typing the parameter is optional do not have to specify it is string""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_ApplicationController_addFromAPI7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/add/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_addFromAPI7_invoker = createInvoker(
    ApplicationController_0.addFromAPI(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "addFromAPI",
      Seq(classOf[String]),
      "GET",
      this.prefix + """github/users/add/""" + "$" + """login<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_ApplicationController_showUser8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/userspage/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_showUser8_invoker = createInvoker(
    ApplicationController_0.showUser(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "showUser",
      Seq(classOf[String]),
      "GET",
      this.prefix + """github/userspage/""" + "$" + """login<[^/]+>""",
      """displaying on HTML""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_ApplicationController_usersRepos9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos")))
  )
  private[this] lazy val controllers_ApplicationController_usersRepos9_invoker = createInvoker(
    ApplicationController_0.usersRepos(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "usersRepos",
      Seq(classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_ApplicationController_usersRepoInfo10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/"), DynamicPart("repoName", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_usersRepoInfo10_invoker = createInvoker(
    ApplicationController_0.usersRepoInfo(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "usersRepoInfo",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/""" + "$" + """repoName<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:19
  private[this] lazy val controllers_ApplicationController_repoContent11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_repoContent11_invoker = createInvoker(
    ApplicationController_0.repoContent(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "repoContent",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_ApplicationController_dirContent12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/"), DynamicPart("dirName", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_dirContent12_invoker = createInvoker(
    ApplicationController_0.dirContent(fakeValue[String], fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "dirContent",
      Seq(classOf[String], classOf[String], classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/""" + "$" + """dirName<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_ApplicationController_fileContent13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/file/"), DynamicPart("filePath", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_fileContent13_invoker = createInvoker(
    ApplicationController_0.fileContent(fakeValue[String], fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "fileContent",
      Seq(classOf[String], classOf[String], classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/""" + "$" + """filePath<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:24
  private[this] lazy val controllers_ApplicationController_createFile14_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/file/create/"), DynamicPart("filePath", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_createFile14_invoker = createInvoker(
    ApplicationController_0.createFile(fakeValue[String], fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "createFile",
      Seq(classOf[String], classOf[String], classOf[String]),
      "PUT",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/create/""" + "$" + """filePath<[^/]+>""",
      """paths for create-or-update-file-contents""",
      Seq()
    )
  )

  // @LINE:25
  private[this] lazy val controllers_ApplicationController_updateFile15_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/file/update/"), DynamicPart("filePath", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_updateFile15_invoker = createInvoker(
    ApplicationController_0.updateFile(fakeValue[String], fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "updateFile",
      Seq(classOf[String], classOf[String], classOf[String]),
      "PUT",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/update/""" + "$" + """filePath<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_ApplicationController_deleteFile16_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/file/delete/"), DynamicPart("filePath", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_deleteFile16_invoker = createInvoker(
    ApplicationController_0.deleteFile(fakeValue[String], fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "deleteFile",
      Seq(classOf[String], classOf[String], classOf[String]),
      "DELETE",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/file/delete/""" + "$" + """filePath<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:27
  private[this] lazy val controllers_ApplicationController_repoReadMe17_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/get/readme")))
  )
  private[this] lazy val controllers_ApplicationController_repoReadMe17_invoker = createInvoker(
    ApplicationController_0.repoReadMe(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "repoReadMe",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/get/readme""",
      """""",
      Seq()
    )
  )

  // @LINE:28
  private[this] lazy val controllers_ApplicationController_dirReadMe18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/get/readme/"), DynamicPart("dir", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_dirReadMe18_invoker = createInvoker(
    ApplicationController_0.dirReadMe(fakeValue[String], fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "dirReadMe",
      Seq(classOf[String], classOf[String], classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/get/readme/""" + "$" + """dir<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:30
  private[this] lazy val controllers_ApplicationController_downloadTar19_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/download/tarball")))
  )
  private[this] lazy val controllers_ApplicationController_downloadTar19_invoker = createInvoker(
    ApplicationController_0.downloadTar(fakeValue[String], fakeValue[String], fakeValue[Option[String]]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "downloadTar",
      Seq(classOf[String], classOf[String], classOf[Option[String]]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/download/tarball""",
      """optional parameters if default overridden in url with ?=""",
      Seq()
    )
  )

  // @LINE:31
  private[this] lazy val controllers_ApplicationController_downloadZip20_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true), StaticPart("/repos/"), DynamicPart("repoName", """[^/]+""",true), StaticPart("/download/zipball")))
  )
  private[this] lazy val controllers_ApplicationController_downloadZip20_invoker = createInvoker(
    ApplicationController_0.downloadZip(fakeValue[String], fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "downloadZip",
      Seq(classOf[String], classOf[String], classOf[String]),
      "GET",
      this.prefix + """github/users/""" + "$" + """login<[^/]+>/repos/""" + "$" + """repoName<[^/]+>/download/zipball""",
      """""",
      Seq()
    )
  )

  // @LINE:35
  private[this] lazy val controllers_Assets_versioned21_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned21_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:2
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_2.index())
      }
  
    // @LINE:5
    case controllers_ApplicationController_index1_route(params@_) =>
      call { 
        controllers_ApplicationController_index1_invoker.call(ApplicationController_0.index())
      }
  
    // @LINE:6
    case controllers_ApplicationController_read2_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_read2_invoker.call(ApplicationController_0.read(login))
      }
  
    // @LINE:7
    case controllers_ApplicationController_create3_route(params@_) =>
      call { 
        controllers_ApplicationController_create3_invoker.call(ApplicationController_0.create())
      }
  
    // @LINE:8
    case controllers_ApplicationController_update4_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_update4_invoker.call(ApplicationController_0.update(login))
      }
  
    // @LINE:9
    case controllers_ApplicationController_delete5_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_delete5_invoker.call(ApplicationController_0.delete(login))
      }
  
    // @LINE:12
    case controllers_ApplicationController_readFromAPI6_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_readFromAPI6_invoker.call(ApplicationController_0.readFromAPI(login))
      }
  
    // @LINE:13
    case controllers_ApplicationController_addFromAPI7_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_addFromAPI7_invoker.call(ApplicationController_0.addFromAPI(login))
      }
  
    // @LINE:16
    case controllers_ApplicationController_showUser8_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_showUser8_invoker.call(ApplicationController_0.showUser(login))
      }
  
    // @LINE:17
    case controllers_ApplicationController_usersRepos9_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_usersRepos9_invoker.call(ApplicationController_0.usersRepos(login))
      }
  
    // @LINE:18
    case controllers_ApplicationController_usersRepoInfo10_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None)) { (login, repoName) =>
        controllers_ApplicationController_usersRepoInfo10_invoker.call(ApplicationController_0.usersRepoInfo(login, repoName))
      }
  
    // @LINE:19
    case controllers_ApplicationController_repoContent11_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None)) { (login, repoName) =>
        controllers_ApplicationController_repoContent11_invoker.call(ApplicationController_0.repoContent(login, repoName))
      }
  
    // @LINE:20
    case controllers_ApplicationController_dirContent12_route(params@_) =>
      call(params.fromPath[String]("dirName", None), params.fromPath[String]("login", None), params.fromPath[String]("repoName", None)) { (dirName, login, repoName) =>
        controllers_ApplicationController_dirContent12_invoker.call(ApplicationController_0.dirContent(dirName, login, repoName))
      }
  
    // @LINE:21
    case controllers_ApplicationController_fileContent13_route(params@_) =>
      call(params.fromPath[String]("filePath", None), params.fromPath[String]("login", None), params.fromPath[String]("repoName", None)) { (filePath, login, repoName) =>
        controllers_ApplicationController_fileContent13_invoker.call(ApplicationController_0.fileContent(filePath, login, repoName))
      }
  
    // @LINE:24
    case controllers_ApplicationController_createFile14_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None), params.fromPath[String]("filePath", None)) { (login, repoName, filePath) =>
        controllers_ApplicationController_createFile14_invoker.call(ApplicationController_0.createFile(login, repoName, filePath))
      }
  
    // @LINE:25
    case controllers_ApplicationController_updateFile15_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None), params.fromPath[String]("filePath", None)) { (login, repoName, filePath) =>
        controllers_ApplicationController_updateFile15_invoker.call(ApplicationController_0.updateFile(login, repoName, filePath))
      }
  
    // @LINE:26
    case controllers_ApplicationController_deleteFile16_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None), params.fromPath[String]("filePath", None)) { (login, repoName, filePath) =>
        controllers_ApplicationController_deleteFile16_invoker.call(ApplicationController_0.deleteFile(login, repoName, filePath))
      }
  
    // @LINE:27
    case controllers_ApplicationController_repoReadMe17_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None)) { (login, repoName) =>
        controllers_ApplicationController_repoReadMe17_invoker.call(ApplicationController_0.repoReadMe(login, repoName))
      }
  
    // @LINE:28
    case controllers_ApplicationController_dirReadMe18_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None), params.fromPath[String]("dir", None)) { (login, repoName, dir) =>
        controllers_ApplicationController_dirReadMe18_invoker.call(ApplicationController_0.dirReadMe(login, repoName, dir))
      }
  
    // @LINE:30
    case controllers_ApplicationController_downloadTar19_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None), params.fromQuery[Option[String]]("branch", None)) { (login, repoName, branch) =>
        controllers_ApplicationController_downloadTar19_invoker.call(ApplicationController_0.downloadTar(login, repoName, branch))
      }
  
    // @LINE:31
    case controllers_ApplicationController_downloadZip20_route(params@_) =>
      call(params.fromPath[String]("login", None), params.fromPath[String]("repoName", None), params.fromQuery[String]("branch", Some("main"))) { (login, repoName, branch) =>
        controllers_ApplicationController_downloadZip20_invoker.call(ApplicationController_0.downloadZip(login, repoName, branch))
      }
  
    // @LINE:35
    case controllers_Assets_versioned21_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned21_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
