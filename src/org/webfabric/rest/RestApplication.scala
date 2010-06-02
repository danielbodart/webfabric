package org.webfabric.rest

import org.webfabric.collections.List
import org.webfabric.properties.PropertiesModule
import com.googlecode.yadic.SimpleContainer
import org.webfabric.gae.GaeModule

class RestApplication(instances: Object*) extends Application {
  lazy val engine = applicationScope.resolveType(classOf[RestEngine])
  val applicationScope = new SimpleContainer
  val modules = List[Module]()

  add(new CoreModule)
  add(new GaeModule)
  add(new PropertiesModule)

  def handle(request: Request, response: Response) = {
    val requestScope = new SimpleContainer(applicationScope)
    modules.foreach(_.addPerRequestObjects(requestScope))
    engine.handle(requestScope, request, response)
  }

  def add(module: Module) {
    module.addPerApplicationObjects(applicationScope)
    module.addResources(engine)
    modules.add(module)
  }

  def addInstances {
    instances.foreach(instance => {
      applicationScope.remove(instance.getClass)
      applicationScope.addInstance(instance)
    })
  }
}