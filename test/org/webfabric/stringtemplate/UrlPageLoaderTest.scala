package org.webfabric.stringtemplate

import org.junit.Test
import org.junit.Assert.{fail}

class UrlPageLoaderTest {
  @Test
  def returnsNoPageIfUrlIsInvalid {
    // setup
    val loader = new UrlPageLoader
    // execute
    loader.load("invalid url") match {
      case Some(page) => fail("should not be able to load an invalid url")
      case _ =>
    }
  }

  @Test
  def returnsNoPageIfUrlIsNull {
    // setup
    val loader = new UrlPageLoader
    // execute
    loader.load(null) match {
      case Some(page) => fail("should not be able to load an invalid url")
      case _ =>
    }
  }
}