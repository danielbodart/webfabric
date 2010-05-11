package org.webfabric.properties

import java.util.{Set, HashSet}
import org.restlet.ext.jaxrs.JaxRsApplication

class PropertiesApplication extends JaxRsApplication {
  def getClasses(): Set[Class[_]] = {
    val set = new HashSet[Class[_]]
    set.add(classOf[PropertiesResource])
    set
  }
}