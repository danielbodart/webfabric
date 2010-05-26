package org.webfabric.properties

import java.util.UUID

case class WebId(uuid:UUID){
  override def toString = uuid.toString
}

object WebId{
  def valueOf(value:String) = new WebId(UUID.fromString(value))
}