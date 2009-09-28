package org.webfabric.servlet

import javax.servlet.http.HttpServletRequest
import java.util.{Map => JavaMap}

class QueryString(val map:JavaMap[String,Array[String]])

object QueryString {
  def apply(request: HttpServletRequest):QueryString = {
    new QueryString(request.getParameterMap.asInstanceOf[JavaMap[String,Array[String]]])
  }
}