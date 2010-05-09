package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import java.util.{Properties}
import org.webfabric.collections.Map
import java.io.{StringReader, Writer, StringWriter}

class PropertiesPresenterTest extends LocalDatastore {
  @Test
  def get {
    // setup
    val repository = new PropertiesRepository(datastoreService)
    val expected = new Properties
    expected.setProperty("foo", "bar")
    var uuid = repository.set(null, expected)

    val presenter = new PropertiesPresenter(repository)

    // execute
    var result = present(presenter, Map("uuid" -> Array(uuid.toString)))

    // verify
    var actual = new Properties()
    actual.load(new StringReader(result))
    assertThat(actual, is(expected))
  }

  def present(presenter: Presenter[java.util.Map[String, Array[String]], Writer],
              map: java.util.Map[String, Array[String]]): String = {
    val writer = new StringWriter
    presenter.present(map, writer)
    writer.toString
  }

  def toString(properties: Properties): String = {
    val writer = new StringWriter
    properties.store(writer, null)
    writer.toString
  }
}