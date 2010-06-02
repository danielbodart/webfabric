package org.webfabric.rest

import javax.ws.rs.Path
import java.lang.reflect.Method

class PathMatcher(method:Method) extends Matcher[Request] {
  lazy val pathTemplate: UriTemplate = {
      val paths = List(method.getDeclaringClass.getAnnotation(classOf[Path]), method.getAnnotation(classOf[Path]))
      new UriTemplate(paths.filter(_ != null).map(_.value).mkString("/"))
    }

  def isMatch(request: Request) = pathTemplate.isMatch(removeLeadingSlash(request.path))

  def removeLeadingSlash(path:String):String = {
    path.replaceFirst("^/", "")
  }
}