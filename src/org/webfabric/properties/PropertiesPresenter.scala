package org.webfabric.properties

import java.io.Writer
import java.lang.String
import java.util.UUID

class PropertiesPresenter(repository: PropertiesRepository) extends Presenter[java.util.Map[String, Object], Writer] {
  def present(request: java.util.Map[String, Object], response: Writer) = {
    var uuid = UUID.fromString(request.get("uuid").toString)
    var properties = repository.get(uuid)
    properties.store(response, null)
  }
}