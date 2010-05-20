package org.webfabric.rest

import javax.ws.rs.FormParam

class FormParameterExtractor(param:FormParam) extends ParameterExtractor{
  def isMatch(request:Request):Boolean = request.form.contains(param.value)
  def extract(request:Request):Object = request.form.getValue(param.value)
}