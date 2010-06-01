package org.webfabric.rest.coercers

import java.lang.Class
import org.webfabric.collections.List

class Coercers extends TypeCoercer {
  val coercers = List[TypeCoercer](new PassThroughCoercer, new ReflectionTypeCoercer)

  def add(coercer: TypeCoercer): Coercers = {
    coercers.add(coercer)
    this
  }

  def coerce(value: Object, result: Class[_]) = coercers.pick(coercer => try {
    Some(coercer.coerce(value, result))
  } catch {
    case e:Exception => None
  })

  def canCoerce(value: Object, result: Class[_]) = coercers.exists(_.canCoerce(value, result))
}