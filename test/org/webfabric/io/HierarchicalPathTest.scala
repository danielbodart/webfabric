package org.webfabric.io


import org.junit.Test
import org.junit.Assert.{assertEquals}

class HierarchicalPathTest {
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