package org.webfabric.properties

import com.googlecode.yadic.Container
import org.antlr.stringtemplate.StringTemplateGroup
import org.webfabric.stringtemplate.UrlStringTemplateGroup
import org.webfabric.io.RelativeResource
import org.webfabric.rest.{Engine, RestEngine, Module, Request}

class PropertiesModule extends Module{
  def addPerRequestObjects(container: Container) = {
    container.add(classOf[PropertiesResource])
    container.add(classOf[PropertiesRepository])
    container.add(classOf[StringTemplateGroup], () => new UrlStringTemplateGroup("templates", baseUrl))
    this
  }

  def addPerApplicationObjects(container: Container) = this


  def addResources(engine: Engine) = {
    engine.add(classOf[PropertiesResource])
    this
  }

  def baseUrl = {
    val url = RelativeResource.asUrl(classOf[PropertiesModule], "editor.st")
    url.replacePath(url.path.parent)
  }
}