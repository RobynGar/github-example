// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/conf/routes
// @DATE:Mon Jul 11 15:15:12 BST 2022

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:2
package controllers {

  // @LINE:4
  class ReverseApplicationController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def read(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:6
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "github/users/create")
    }
  
    // @LINE:10
    def readFromAPI(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/api/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:11
    def addFromAPI(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/add/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:17
    def dirContent(dirName:String, login:String, repoName:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)) + "/repos/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("repoName", repoName)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("dirName", dirName)))
    }
  
    // @LINE:16
    def repoContent(login:String, repoName:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)) + "/repos/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("repoName", repoName)))
    }
  
    // @LINE:8
    def delete(login:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:13
    def showUser(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/userspage/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:7
    def update(login:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)))
    }
  
    // @LINE:15
    def usersRepoInfo(login:String, repoName:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("repoName", repoName)))
    }
  
    // @LINE:4
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users")
    }
  
    // @LINE:14
    def usersRepos(login:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "github/users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("login", login)) + "/repositories")
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

  // @LINE:21
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:21
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
