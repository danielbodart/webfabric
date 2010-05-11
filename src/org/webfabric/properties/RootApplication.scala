package org.webfabric.properties

import org.restlet.ext.jaxrs.JaxRsApplication
import org.restlet.Context

class RootApplication(context:Context) extends JaxRsApplication(context){
  add(new PropertiesApplication)
}