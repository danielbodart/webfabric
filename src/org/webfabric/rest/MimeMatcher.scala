package org.webfabric.rest

import javax.ws.rs.Produces
import java.lang.reflect.Method

class MimeMatcher(resource:Class[_], method:Method) extends Matcher[Request] {
  lazy val producesMimetype: String = {
    val result = List(method.getAnnotation(classOf[Produces]), resource.getAnnotation(classOf[Produces])).filter(_ != null)
    result.headOption match {
      case Some(produces) => produces.value.first
      case None => "*/*"
    }
  }

  def isMatch(request: Request):Boolean = {
    val expected = if(request.headers.contains("Accept")) request.headers.getValue("Accept") else "*/*"
    expected.equals(producesMimetype)
  }
}