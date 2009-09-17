package org.webfabric.io

import org.junit.Assert.{assertEquals}
import org.junit.Test

class UrlTest{
  @Test
  def applyRemovesDuplicatePathSeperators(): Unit = {
    // setup
    val url = Url("jar:file:/someFolder/someJar.jar!/folder/in//jar/html.st")    

    // execute & verify
    assertEquals(Url("jar:file:/someFolder/someJar.jar!/folder/in/jar/html.st"), url)
  }

  @Test
  def canReplacePathOnJarUrls(): Unit = {
    // setup
    val url = new Url("jar:file:/someFolder/someJar.jar!/folder/in/jar/html.st")    

    // execute
    val result = url.replacePath(url.path.parent)

    // verify
    assertEquals(new Url("jar:file:/someFolder/someJar.jar!/folder/in/jar/"), result)
  }

  @Test
  def canReplacePath(): Unit = {
    // setup
    val url = new Url("http://webfabric.org/foo/bar")

    // execute
    val result = url.replacePath(url.path.parent)

    // verify
    assertEquals(new Url("http://webfabric.org/foo/"), result)
  }
  
    @Test
  def canGetParentOnJarUrls(): Unit = {
    // setup
    val url = new Url("jar:file:/someFolder/someJar.jar!/folder/in/jar/html.st")

    // execute
    val result = url.parent

    // verify
    assertEquals(new Url("jar:file:/someFolder/someJar.jar!/folder/in/jar/"), result)
  }

  @Test
  def canGetParent(): Unit = {
    // setup
    val url = new Url("http://webfabric.org/foo/bar")

    // execute
    val result = url.parent

    // verify
    assertEquals(new Url("http://webfabric.org/foo/"), result)
  }
}