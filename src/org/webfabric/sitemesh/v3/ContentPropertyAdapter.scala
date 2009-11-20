package org.webfabric.sitemesh.v3

import org.sitemesh.content.ContentProperty
import java.lang.String
import org.webfabric.sitemesh.Property

class ContentPropertyAdapter(property:ContentProperty) extends Property{
  def getValue = property.getValue

  def getChild(name: String) = new ContentPropertyAdapter(property.getChild(name))

  def hasChild(name: String) = property.hasChild(name)
}