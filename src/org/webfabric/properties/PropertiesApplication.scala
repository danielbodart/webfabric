package org.webfabric.properties

import org.webfabric.rest.RestApplication
import org.webfabric.gae.GaeModule

class PropertiesApplication extends RestApplication{
  add(new GaeModule)
  add(new PropertiesModule)
}