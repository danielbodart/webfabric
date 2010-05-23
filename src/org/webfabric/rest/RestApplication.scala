package org.webfabric.rest

import org.webfabric.collections.List
import org.webfabric.properties.PropertiesModule
import com.googlecode.yadic.SimpleContainer

class RestApplication extends Application{
  val engine = new RestEngine
  val applicationScope = new SimpleContainer
  val modules = List[Module]()

  add(new PropertiesModule)

  def handle(request: Request, response: Response) = {
    val requestScope = new SimpleContainer(applicationScope)
    modules.foreach(_.addPerRequestObjects(requestScope))
    engine.handle(requestScope, request, response)
  }
  
  def add(module:Module){
    module.addPerApplicationObjects(applicationScope)
    module.addResource(engine)
    modules.add(module)
  }
}