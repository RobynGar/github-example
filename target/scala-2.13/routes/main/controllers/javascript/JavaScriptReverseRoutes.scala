// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/robyn.garlington/Documents/scalaTraining/assignments/gHub-example/gitHub-ex/conf/routes
// @DATE:Tue Jul 12 12:52:10 BST 2022

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:2
package controllers.javascript {

  // @LINE:4
  class ReverseApplicationController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def read: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.read",
      """
        function(login0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0))})
        }
      """
    )
  
    // @LINE:6
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/create"})
        }
      """
    )
  
    // @LINE:10
    def readFromAPI: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.readFromAPI",
      """
        function(login0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/api/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0))})
        }
      """
    )
  
    // @LINE:18
    def fileContent: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.fileContent",
      """
        function(filePath0,login1,repoName2) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login1)) + "/repos/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("repoName", repoName2)) + "/file/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("filePath", filePath0))})
        }
      """
    )
  
    // @LINE:11
    def addFromAPI: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.addFromAPI",
      """
        function(login0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/add/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0))})
        }
      """
    )
  
    // @LINE:17
    def dirContent: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.dirContent",
      """
        function(dirName0,login1,repoName2) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login1)) + "/repos/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("repoName", repoName2)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("dirName", dirName0))})
        }
      """
    )
  
    // @LINE:16
    def repoContent: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.repoContent",
      """
        function(login0,repoName1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0)) + "/repos/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("repoName", repoName1))})
        }
      """
    )
  
    // @LINE:8
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.delete",
      """
        function(login0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0))})
        }
      """
    )
  
    // @LINE:13
    def showUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.showUser",
      """
        function(login0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/userspage/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0))})
        }
      """
    )
  
    // @LINE:7
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.update",
      """
        function(login0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0))})
        }
      """
    )
  
    // @LINE:15
    def usersRepoInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.usersRepoInfo",
      """
        function(login0,repoName1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("repoName", repoName1))})
        }
      """
    )
  
    // @LINE:4
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users"})
        }
      """
    )
  
    // @LINE:14
    def usersRepos: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApplicationController.usersRepos",
      """
        function(login0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "github/users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("login", login0)) + "/repos"})
        }
      """
    )
  
  }

  // @LINE:2
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:2
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:22
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:22
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }


}
