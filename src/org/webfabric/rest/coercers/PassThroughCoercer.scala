package org.webfabric.rest.coercers

import java.lang.Class

class PassThroughCoercer extends TypeCoercer {
  def canCoerce(value: Object, result: Class[_]) = result.isAssignableFrom(value.getClass)

  def coerce(value: Object, result: Class[_]): Object = value
}