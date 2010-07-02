package org.webfabric.rest

import org.webfabric.servlet.{ContextPath, BasePath, WarRoot}
import javax.servlet.{ServletContext, ServletContextEvent, ServletContextListener}
import org.webfabric.properties.PropertiesApplication

class ApplicationStarter extends ServletContextListener{
  def createApplication(servletContext: ServletContext): Application = {
    new PropertiesApplication
  }

  def contextInitialized(event: ServletContextEvent) = {
    val context = event.getServletContext
    val application = createApplication(context).addInstance(WarRoot(context)).addInstance(ContextPath(context))
    context.setAttribute(classOf[Application].getCanonicalName, application)
  }
  
  def contextDestroyed(event: ServletContextEvent) = {}
}