package org.webfabric.properties

import java.io.Writer
import java.lang.String
import java.util.UUID

class PropertiesPresenter(repository: PropertiesRepository) extends Presenter[java.util.Map[String, Array[String]], Writer] {
  def present(request: java.util.Map[String, Array[String]], response: Writer) = {
    var uuid = UUID.fromString(request.get("uuid")(0))
    var properties = repository.get(uuid)
    properties.store(response, null)
  }
}