package org.webfabric.gae

import com.googlecode.yadic.Container
import com.google.appengine.api.datastore.{DatastoreService, DatastoreServiceFactory}
import org.webfabric.rest.{Engine, RestEngine, Module}

class GaeModule extends Module{
  def addResources(engine: Engine) = this

  def addPerApplicationObjects(container: Container) = this

  def addPerRequestObjects(container: Container) = {
    container.add(classOf[DatastoreService], () => DatastoreServiceFactory.getDatastoreService)
    this
  }
}