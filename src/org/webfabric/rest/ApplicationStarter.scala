package org.webfabric.rest

import javax.servlet.{ServletContextEvent, ServletContextListener}
import org.webfabric.servlet.{ContextPath, BasePath, WarRoot}

class ApplicationStarter extends ServletContextListener{
  def contextInitialized(event: ServletContextEvent) = {
    val context = event.getServletContext
    val application = new RestApplication(WarRoot(context), ContextPath(context))
    context.setAttribute(classOf[Application].getCanonicalName, application)
  }
  
  def contextDestroyed(event: ServletContextEvent) = {}
}