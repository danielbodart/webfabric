package org.webfabric.rest

import com.googlecode.yadic.Container


class CoreModule extends Module{
  def addResources(engine: RestEngine) = this

  def addPerApplicationObjects(container: Container) = {
    container.add(classOf[RestEngine])
    this
  }

  def addPerRequestObjects(container: Container) = this
}