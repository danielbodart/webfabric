package org.webfabric.rest

import java.lang.reflect.Method
import javax.ws.rs.{Consumes}
import javax.ws.rs.core.MediaType

class ConsumesMimeMatcher(resource: Class[_], method: Method) extends Matcher[Request] {
  lazy val mimeType: String = {
    List(method.getAnnotation(classOf[Consumes]), resource.getAnnotation(classOf[Consumes])).filter(_ != null) match {
      case x :: xs => x.value.first
      case Nil => MediaType.WILDCARD
    }
  }

  def isMatch(request: Request): Boolean = {
    if (mimeType.equals(MediaType.WILDCARD)) true else
    if (request.headers.contains("Content-Type")) request.headers.getValue("Content-Type").equals(mimeType) else true
  }
}