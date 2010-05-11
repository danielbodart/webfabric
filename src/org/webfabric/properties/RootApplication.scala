package org.webfabric.properties

import org.restlet.Context
import org.restlet.ext.jaxrs.{JaxRsApplication}
class RootApplication(context:Context) extends JaxRsApplication(context){
  val application: PropertiesApplication = new PropertiesApplication
  add(application)
  setObjectFactory(application)
}