package org.webfabric.io


import org.junit.Test
import org.junit.Assert.{assertEquals, assertTrue}

class HierarchicalPathTest {
  @Test
  def supportsToString(): Unit = {
    assertEquals("/", new HierarchicalPath("/").toString)
    assertEquals("", new HierarchicalPath("").toString)
    assertEquals("/foo/bar/", new HierarchicalPath("/foo/bar/").toString)
    assertTrue("/foo/bar/" != new HierarchicalPath("/foo/bar/"))
  }

  @Test
  def providesChildren(): Unit = {
    // setup
    val parent = new HierarchicalPath("/foo/")

    // execute
    val child = parent.child("bar")

    // verify
    assertEquals(new HierarchicalPath("/foo/bar/"), child)
  }

  @Test
  def providesParent(): Unit = {
    // setup
    val child = new HierarchicalPath("/foo/bar")

    // execute
    val parent = child.parent

    // verify
    assertEquals(new HierarchicalPath("/foo/"), parent)
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