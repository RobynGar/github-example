// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/conf/routes
// @DATE:Tue Jul 05 12:22:40 BST 2022

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:2
  HomeController_2: controllers.HomeController,
  // @LINE:3
  ApplicationController_0: controllers.ApplicationController,
  // @LINE:12
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:2
    HomeController_2: controllers.HomeController,
    // @LINE:3
    ApplicationController_0: controllers.ApplicationController,
    // @LINE:12
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
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github""", """controllers.ApplicationController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.read(login:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/api/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.readFromAPI(login:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/add/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.addFromAPI(login:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users""", """controllers.ApplicationController.create()"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.update(login:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """github/users/""" + "$" + """login<[^/]+>""", """controllers.ApplicationController.delete(login:String)"""),
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

  // @LINE:3
  private[this] lazy val controllers_ApplicationController_index1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github")))
  )
  private[this] lazy val controllers_ApplicationController_index1_invoker = createInvoker(
    ApplicationController_0.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "index",
      Nil,
      "GET",
      this.prefix + """github""",
      """""",
      Seq()
    )
  )

  // @LINE:4
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

  // @LINE:5
  private[this] lazy val controllers_ApplicationController_readFromAPI3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/api/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_readFromAPI3_invoker = createInvoker(
    ApplicationController_0.readFromAPI(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "readFromAPI",
      Seq(classOf[String]),
      "GET",
      this.prefix + """github/users/api/""" + "$" + """login<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_ApplicationController_addFromAPI4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/add/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_addFromAPI4_invoker = createInvoker(
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

  // @LINE:7
  private[this] lazy val controllers_ApplicationController_create5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users")))
  )
  private[this] lazy val controllers_ApplicationController_create5_invoker = createInvoker(
    ApplicationController_0.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "create",
      Nil,
      "POST",
      this.prefix + """github/users""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_ApplicationController_update6_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_update6_invoker = createInvoker(
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
  private[this] lazy val controllers_ApplicationController_delete7_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("github/users/"), DynamicPart("login", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_delete7_invoker = createInvoker(
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
  private[this] lazy val controllers_Assets_versioned8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned8_invoker = createInvoker(
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
  
    // @LINE:3
    case controllers_ApplicationController_index1_route(params@_) =>
      call { 
        controllers_ApplicationController_index1_invoker.call(ApplicationController_0.index())
      }
  
    // @LINE:4
    case controllers_ApplicationController_read2_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_read2_invoker.call(ApplicationController_0.read(login))
      }
  
    // @LINE:5
    case controllers_ApplicationController_readFromAPI3_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_readFromAPI3_invoker.call(ApplicationController_0.readFromAPI(login))
      }
  
    // @LINE:6
    case controllers_ApplicationController_addFromAPI4_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_addFromAPI4_invoker.call(ApplicationController_0.addFromAPI(login))
      }
  
    // @LINE:7
    case controllers_ApplicationController_create5_route(params@_) =>
      call { 
        controllers_ApplicationController_create5_invoker.call(ApplicationController_0.create())
      }
  
    // @LINE:8
    case controllers_ApplicationController_update6_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_update6_invoker.call(ApplicationController_0.update(login))
      }
  
    // @LINE:9
    case controllers_ApplicationController_delete7_route(params@_) =>
      call(params.fromPath[String]("login", None)) { (login) =>
        controllers_ApplicationController_delete7_invoker.call(ApplicationController_0.delete(login))
      }
  
    // @LINE:12
    case controllers_Assets_versioned8_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned8_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
