package org.webfabric.rest

import javax.ws.rs.Produces
import java.lang.reflect.Method

class ProducesMimeMatcher(resource:Class[_], method:Method) extends Matcher[Request] {
  lazy val mimeType: String = {
    List(method.getAnnotation(classOf[Produces]), resource.getAnnotation(classOf[Produces])).filter(_ != null) match {
      case x :: xs => x.value.first
      case Nil => "*/*"
    }
  }

  def isMatch(request: Request):Boolean = {
    val expected = if(request.headers.contains("Accept")) request.headers.getValue("Accept") else "*/*"
    expected.equals(mimeType)
  }
}