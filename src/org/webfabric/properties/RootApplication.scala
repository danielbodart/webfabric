package org.webfabric.properties

import org.restlet.ext.jaxrs.{JaxRsApplication}

class RootApplication extends JaxRsApplication {
  val application = new PropertiesApplication
  add(application)
  setObjectFactory(application)
}