package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import java.util.{UUID, Properties}
import com.google.appengine.api.datastore.EntityNotFoundException

class PropertiesRepositoryTest extends LocalDatastore {
  @Test
  def storesProperties {
    // setup
    val properties = new Properties
    var name = "name"
    var value = "value"
    properties.setProperty(name, value)
    val repository = new PropertiesRepository(datastoreService)

    // execute
    val key = repository.set(null, properties)
    val result = repository.get(key)

    // verify
    assertThat(result.getProperty(name), is(value))
  }

  @Test
  def storesPropertiesWithKeyProvided {
    // setup
    val properties = new Properties
    var name = "name"
    var value = "value"
    properties.setProperty(name, value)
    val repository = new PropertiesRepository(datastoreService)

    // execute
    var key = UUID.randomUUID
    assertThat(repository.set(key, properties), is(key))
    val result = repository.get(key)

    // verify
    assertThat(result.getProperty(name), is(value))
  }

  @Test
  def invalidKey {
    // setup
    val repository = new PropertiesRepository(datastoreService)

    // execute
    var key = UUID.randomUUID
    var properties = repository.get(key)
    
    // verify
    assertThat(properties, is(empty))
  }

  val empty = new Properties
}