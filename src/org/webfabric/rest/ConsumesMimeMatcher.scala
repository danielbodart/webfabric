package org.webfabric.rest

import java.lang.reflect.Method
import javax.ws.rs.{Consumes}

class ConsumesMimeMatcher(resource:Class[_], method:Method) extends Matcher[Request] {
  lazy val producesMimetype: String = {
    List(method.getAnnotation(classOf[Consumes]), resource.getAnnotation(classOf[Consumes])).filter(_ != null) match {
      case x :: xs => x.value.first
      case Nil => "*/*"
    }
  }

  def isMatch(request: Request):Boolean = {
    val expected = if(request.headers.contains("Content-Type")) request.headers.getValue("Content-Type") else "*/*"
    expected.equals(producesMimetype)
  }
}