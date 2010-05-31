package org.webfabric.properties

import java.util.UUID

class Id private(val value:String){
  private def this (uuid:UUID) = this(uuid.toString)

  override def toString = value

  override def hashCode = value.hashCode

  override def equals(o: Any) = o match{
    case other:Id => value.equals(other.value)
    case _ => false
  }
}

object Id{
  def apply(value:String):Id = value match {
    case "new" => new Id("new")
    case _ => new Id(UUID.fromString(value))
  }

  def apply():Id = new Id(UUID.randomUUID)
}

