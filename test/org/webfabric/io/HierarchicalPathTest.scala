package org.webfabric.io


import org.junit.Test
import org.junit.Assert.{assertEquals, assertTrue}

class HierarchicalPathTest {
  @Test
  def applyRemovesDuplicateSeperators(): Unit = {
    // setup
    val path = HierarchicalPath("/folder/in//jar/html.st")

    // execute & verify
    assertEquals(HierarchicalPath("/folder/in/jar/html.st"), path)
  }

  @Test
  def supportsToString(): Unit = {
    assertEquals("/", new HierarchicalPath("/").toString)
    assertEquals("", new HierarchicalPath("").toString)
    assertEquals("/foo/bar/", new HierarchicalPath("/foo/bar/").toString)
    assertTrue("/foo/bar/" != new HierarchicalPath("/foo/bar/"))
  }

  @Test
  def supportsSubDirectories(): Unit = {
    assertEquals(new HierarchicalPath("/foo/bar/"), new HierarchicalPath("/foo/").subDirectory("bar"))
    assertEquals(new HierarchicalPath("/foo/bar/"), new HierarchicalPath("/foo").subDirectory("bar"))
    assertEquals(new HierarchicalPath("foo/bar/"), new HierarchicalPath("foo").subDirectory("bar"))
    assertEquals(new HierarchicalPath("foo/bar/"), new HierarchicalPath("foo/").subDirectory("bar"))
//    assertEquals(new HierarchicalPath("/foo/"), new HierarchicalPath("/").subDirectory("foo")) TODO
//    assertEquals(new HierarchicalPath("foo/"), new HierarchicalPath("").subDirectory("foo")) TODO
  }

  @Test
  def supportsFiles(): Unit = {
    assertEquals(new HierarchicalPath("/foo/bar.txt"), new HierarchicalPath("/foo/").file("bar.txt"))
    assertEquals(new HierarchicalPath("foo/bar.txt"), new HierarchicalPath("foo/").file("bar.txt"))
//    assertEquals(new HierarchicalPath("/foo.txt"), new HierarchicalPath("/").file("foo.txt")) TODO
//    assertEquals(new HierarchicalPath("foo.txt"), new HierarchicalPath("").file("foo.txt")) TODO
  }

  @Test
  def providesParent(): Unit = {
    assertEquals(new HierarchicalPath("/foo/"), new HierarchicalPath("/foo/bar").parent)
    assertEquals(new HierarchicalPath("/"), new HierarchicalPath("/foo/").parent)
    assertEquals(new HierarchicalPath("/"), new HierarchicalPath("/foo").parent)
    assertEquals(new HierarchicalPath("/"), new HierarchicalPath("/").parent)
    assertEquals(new HierarchicalPath(""), new HierarchicalPath("").parent)
//    assertEquals(new HierarchicalPath(""), new HierarchicalPath("foo/").parent) TODO
//    assertEquals(new HierarchicalPath(""), new HierarchicalPath("foo").parent) TODO
  }

  @Test
  def providesParentEvenWithTrailingSlash(): Unit = {
    // setup
    val child = new HierarchicalPath("/foo/bar/")

    // execute
    val parent = child.parent

    // verify
    assertEquals(new HierarchicalPath("/foo/"), parent)
  }

  @Test
  def handlesTopLevelPaths(): Unit = {
    // setup
    val child = new HierarchicalPath("/top/")

    // execute
    val parent = child.parent

    // verify
    assertEquals(new HierarchicalPath("/"), parent)
  }

  @Test
  def handlesRootPaths(): Unit = {
    // setup
    val child = new HierarchicalPath("/")

    // execute
    val parent = child.parent

    // verify
    assertEquals(new HierarchicalPath("/"), parent)
  }
}