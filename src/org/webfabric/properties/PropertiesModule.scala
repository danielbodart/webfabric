package org.webfabric.properties

import com.googlecode.yadic.Container
import com.google.appengine.api.datastore.{DatastoreService, DatastoreServiceFactory}
import org.antlr.stringtemplate.StringTemplateGroup
import org.webfabric.stringtemplate.UrlStringTemplateGroup
import org.webfabric.io.RelativeResource
import org.webfabric.rest.{RestEngine, Module, Request}

class PropertiesModule extends Module{
  def addPerRequestObjects(container: Container) = {
    container.add(classOf[PropertiesResource])
    container.add(classOf[PropertiesRepository])
    container.add(classOf[DatastoreService], () => DatastoreServiceFactory.getDatastoreService)
    container.add(classOf[StringTemplateGroup], () => new UrlStringTemplateGroup("templates", baseUrl))
    this
  }

  def addPerApplicationObjects(container: Container) = this


  def addResource(engine: RestEngine) = {
    engine.add(classOf[PropertiesResource])
    this
  }

  def baseUrl = {
    val url = RelativeResource.asUrl(classOf[PropertiesModule], "editor.st")
    url.replacePath(url.path.parent)
  }
}