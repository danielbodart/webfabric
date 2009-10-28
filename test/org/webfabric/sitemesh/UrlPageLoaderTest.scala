package org.webfabric.sitemesh

import org.junit.Test
import org.junit.Assert.{fail}

class UrlPageLoaderTest {
  val implementations = List(new UrlPageLoader(new v2.DivCapturingPageParser), new UrlPageLoader(new v3.ContentPropertyMapParser))

  @Test
  def returnsNoPageIfUrlIsInvalid {
    implementations.foreach(loader => {
      loader.load("invalid url") match {
        case Some(page) => fail("should not be able to load an invalid url")
        case _ =>
      }
    })
  }

  @Test
  def returnsNoPageIfUrlIsNull {
    implementations.foreach(loader => {
      loader.load(null) match {
        case Some(page) => fail("should not be able to load an invalid url")
        case _ =>
      }
    })
  }
}