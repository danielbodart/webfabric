package org.webfabric.properties

import java.util.{UUID, Properties}
import org.webfabric.collections.Map.toIterable
import com.google.appengine.api.datastore._

class PropertiesRepository(datastore: DatastoreService) extends Repository[UUID, Properties] {
  var kind = "properties"

  def convert(key: UUID): Key = {
    KeyFactory.createKey(kind, key.toString)
  }

  def set(key: UUID, properties: Properties): UUID = {
    val k = if (key == null) UUID.randomUUID else key
    val entity = new Entity(convert(k))
    properties.foreach(entry => entity.setProperty(entry.getKey.toString, entry.getValue))
    datastore.put(entity)
    k
  }

  def get(key: UUID): Properties = {
    val properties = new Properties
    try {
      val entity = datastore.get(convert(key))
      entity.getProperties.foreach(entry => properties.setProperty(entry.getKey, entry.getValue.toString))
    } catch {
      case e: EntityNotFoundException => 
    }
    properties
  }
}