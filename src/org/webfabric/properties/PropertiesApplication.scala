package org.webfabric.properties

import org.webfabric.collections.Set
import org.restlet.ext.jaxrs.ObjectFactory
import com.google.appengine.api.datastore.{DatastoreService, DatastoreServiceFactory}
import com.googlecode.yadic.{Container, SimpleContainer}

class PropertiesApplication extends javax.ws.rs.core.Application with ObjectFactory {
  def getClasses = {
    Set[Class[_]](classOf[PropertiesResource])
  }

  def getInstance[T](aClass: Class[T]) = {
    requestScope.resolveType(aClass)
  }

  def requestScope: Container = {
    val requestScope = new SimpleContainer
    requestScope.add(classOf[PropertiesResource])
    requestScope.add(classOf[PropertiesRepository])
    requestScope.add(classOf[DatastoreService], () => DatastoreServiceFactory.getDatastoreService)
  }
}