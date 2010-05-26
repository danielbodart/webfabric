package org.webfabric.gae

import com.googlecode.yadic.Container
import org.webfabric.rest.{RestEngine, Module}
import com.google.appengine.api.datastore.{DatastoreService, DatastoreServiceFactory}

class GaeModule extends Module{
  def addResources(engine: RestEngine) = this

  def addPerApplicationObjects(container: Container) = this

  def addPerRequestObjects(container: Container) = {
    container.add(classOf[DatastoreService], () => DatastoreServiceFactory.getDatastoreService)
    this
  }
}