package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import java.util.{Properties, Map, HashMap}
import java.io.{Writer, StringWriter}

class PropertiesPresenterTest extends LocalDatastore {

  @Test
  def get {
    // setup
    val repository = new PropertiesRepository(datastoreService)
    val properties = new Properties
    properties.setProperty("foo", "bar")
    val expected = toString(properties)
    var uuid = repository.set(null, properties)

    val presenter = new PropertiesPresenter(repository)

    // execute
    var result = present(presenter, map("uuid" -> uuid))

    // verify
    assertThat(result, is(expected))
  }

  def present(presenter: Presenter[Map[String, Object], Writer], map:Map[String, Object]): String = {
    val writer = new StringWriter
    presenter.present(map, writer)
    writer.toString
  }

  def toString(properties:Properties):String ={
    val writer = new StringWriter
    properties.store(writer, null)
    writer.toString
  }

  def map(pairs:(String, Object)*): Map[String, Object] ={
    val result = new HashMap[String, Object]
    pairs.foreach(pair => result.put(pair._1, pair._2))
    result
  }

}