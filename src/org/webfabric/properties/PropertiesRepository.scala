package org.webfabric.properties

import java.util.{Properties}
import org.webfabric.collections.Map.toIterable
import com.google.appengine.api.datastore._

class PropertiesRepository(datastore: DatastoreService) extends Repository[Id, Properties] {
  var kind = "properties"

  def convert(key: Id): Key = {
    KeyFactory.createKey(kind, key.toString)
  }

  def set(key: Id, properties: Properties): Id = {
    val k = if (key == null) Id() else key
    val entity = new Entity(convert(k))
    properties.foreach(entry => entity.setProperty(entry._1.toString, entry._2))
    datastore.put(entity)
    k
  }

  def get(key: Id): Properties = {
    val properties = new Properties
    try {
      val entity = datastore.get(convert(key))
      entity.getProperties.foreach(entry => properties.setProperty(entry._1, entry._2.toString))
    } catch {
      case e: EntityNotFoundException => 
    }
    properties
  }

  def remove(key: Id) {
    datastore.delete(convert(key))
  }
}