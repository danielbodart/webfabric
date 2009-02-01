package org.webfabric.io


import junit.Test
import junit.Assert.{assertEquals}

class UrlTest{
  @Test
  def canReplacePath(): Unit = {
    // setup
    val url = new Url("http://webfabric.org/foo/bar")

    // execute
    val result = url.replacePath(url.path.parent)

    // verify
    assertEquals(new Url("http://webfabric.org/foo"), result)
  }
}