package org.webfabric.rest

import javax.ws.rs.FormParam

class FormParameterExtractor(param:FormParam) extends RequestExtractor[String]{
  def isMatch(request:Request):Boolean = request.form.contains(param.value)
  def extract(request:Request):String = request.form.getValue(param.value)
}