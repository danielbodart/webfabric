package org.webfabric.rest.coercers

import java.lang.Class
import com.googlecode.yadic.SimpleContainer

class ReflectionTypeCoercer extends TypeCoercer{
  def canCoerce(value: Any, result: Class[_]) = true

  def coerce(value: Object, result: Class[_]):Object = {
    new SimpleContainer().add(value).add(result).resolve(result)
  }
}
