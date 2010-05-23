package org.webfabric.rest

import javax.servlet.{ServletContextEvent, ServletContextListener}

class ApplicationStarter extends ServletContextListener{
  def contextInitialized(event: ServletContextEvent) = {
    val application = new RestApplication
    event.getServletContext.setAttribute(classOf[Application].getCanonicalName, application)
  }
  
  def contextDestroyed(event: ServletContextEvent) = {}
}