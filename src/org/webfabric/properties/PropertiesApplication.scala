package org.webfabric.properties

import org.webfabric.collections.Set

class PropertiesApplication extends javax.ws.rs.core.Application {
  def getClasses = {
    Set[Class[_]](classOf[PropertiesResource])
  }
}