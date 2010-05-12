package org.webfabric.properties

import org.webfabric.collections.Set
import org.restlet.ext.jaxrs.ObjectFactory
import com.google.appengine.api.datastore.{DatastoreService, DatastoreServiceFactory}
import com.googlecode.yadic.{Container, SimpleContainer}
import org.antlr.stringtemplate.StringTemplateGroup
import org.webfabric.io.RelativeResource
import org.webfabric.stringtemplate.{UrlStringTemplateGroup}

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
    requestScope.add(classOf[StringTemplateGroup], () => new UrlStringTemplateGroup("templates", baseUrl))
  }

  def baseUrl = {
    val url = RelativeResource.asUrl(classOf[PropertiesApplication], "editor.st")
    url.replacePath(url.path.parent)
  }
}