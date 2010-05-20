package org.webfabric.rest

import javax.ws.rs.Path
import java.lang.reflect.Method

class PathExtractor(resource:Class[_], method:Method) extends Matcher[Request] {
  lazy val pathTemplate: UriTemplate = {
      val paths = List(resource.getAnnotation(classOf[Path]), method.getAnnotation(classOf[Path]))
      new UriTemplate(paths.filter(_ != null).map(_.value).mkString("/"))
    }

  def isMatch(request: Request) = pathTemplate.isMatch(request.path)

  def extract(request: Request) = pathTemplate.extract(request.path)
}