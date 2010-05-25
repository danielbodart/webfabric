package org.webfabric.rest

import javax.ws.rs.Produces
import java.lang.reflect.Method
import javax.ws.rs.core.MediaType

class ProducesMimeMatcher(resource: Class[_], method: Method) extends Matcher[Request] {
  lazy val mimeType: String = {
    List(method.getAnnotation(classOf[Produces]), resource.getAnnotation(classOf[Produces])).filter(_ != null) match {
      case x :: xs => x.value.first
      case Nil => MediaType.WILDCARD
    }
  }

  def isMatch(request: Request): Boolean = {
    if (request.headers.contains("Accept")) {
      Accept(request.headers.getValue("Accept")).contains(mimeType)
    } else true
  }

  def matchQuality(request: Request): Float = {
    if (request.headers.contains("Accept")) {
      var quality = Accept(request.headers.getValue("Accept")).quality(mimeType)
      quality
    } else 1.0f
  }
}