// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/conf/routes
// @DATE:Wed Jul 06 10:21:43 BST 2022

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:2
package controllers {

  // @LINE:3
  class ReverseApplicationController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:4
    def readAll(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users")
    }
  
    // @LINE:5
    def read(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:8
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "github/users/create")
    }
  
    // @LINE:6
    def readFromAPI(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/api/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:7
    def addFromAPI(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/add/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:10
    def delete(login:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:9
    def update(login:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:3
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github")
    }
  
  }

  // @LINE:2
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:2
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:14
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
