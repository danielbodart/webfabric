package org.webfabric.collections

import org.junit.Test
import org.junit.Assert.{assertThat, fail}
import org.hamcrest.CoreMatchers._

class IterableTest {
  @Test
  def supportsHead(): Unit = {
    // setup
    val numbers = List(1,2,4)

    // execute & verify
    assertThat(numbers.head, is(equalTo(1)))
  }

  @Test
  def supportsFind(): Unit = {
    // setup
    val numbers = List(1,2,4)

    // execute
    val result:Option[Int] = numbers.find(_ %2 == 0)

    // verify
    assertThat(result.get, is(equalTo(2)))
  }

  @Test
  def supportsFoldLeft(): Unit = {
    // setup
    val numbers = List(1,2)

    // execute
    val result:Int = numbers.foldLeft[Int](3, _+_)

    // verify
    assertThat(result, is(equalTo(6)))
  }

  @Test
  def comprehensionMethodsCanBeUsedAsAStream(): Unit = {
    // setup
    val numbers = List(() => 1, () => 2, () => throw new AssertionError("Should never get here"))

    // execute
    val results = numbers.map(_()).flatMap((i: Int) => List(i, i + 2)).filter(_ % 2 == 0)

    // verify
    val iterator = results.iterator
    assertThat(iterator.next, is(equalTo(2)))
    assertThat(iterator.next, is(equalTo(4)))
  }

  @Test
  def supportsFlatMap(): Unit = {
    // setup & execute
    val results = List("the", "quick").flatMap(item => List(1, 2))

    // verify
    val iterator = results.iterator
    assertThat(iterator.next, is(equalTo(1)))
    assertThat(iterator.next, is(equalTo(2)))
    assertThat(iterator.next, is(equalTo(1)))
    assertThat(iterator.next, is(equalTo(2)))
  }

  @Test
  def supportsForCondition(): Unit = {
    // setup & execute
    val results = for (item <- List(1, 2, 3, 4) if item % 2 == 0) yield item

    // verify
    for (result <- results) if (result == 1 || result == 3) fail("filter did not work")
  }

  @Test
  def supportsFilter(): Unit = {
    // setup & execute
    val results = List(1, 2, 3, 4).filter(_ % 2 == 0)

    // verify
    val iterator = results.iterator
    assertThat(iterator.next, is(equalTo(2)))
    assertThat(iterator.next, is(equalTo(4)))
  }

  @Test
  def supportsYield(): Unit = {
    // setup & execute
    val results = for (item <- List(3)) yield item * 4

    // verify
    for (result <- results) assertThat(result, is(equalTo(12)))
  }

  @Test
  def supportsMapping(): Unit = {
    // setup & execute
    val results = List(3).map(_ * 4)

    // verify
    for (result <- results) assertThat(result, is(equalTo(12)))
  }

  @Test
  def supportsTryingToPickingTheFirstItemThatSuccessfullyConverts(): Unit = {
    // setup
    val expensiveList = List(
      () => None, // didn't convert
      () => Some("converted"),
      () => error("should never get here")
      )

    // execute
    val option = expensiveList.tryPick(_())

    // verify
    assertThat(option.get, is(equalTo("converted")))
  }

  @Test
  def supportsForEach(): Unit = {
    // setup
    var count = 0

    // execute
    List("bill", "bob").foreach(item => count = count + 1)

    // verify
    assertThat(count, is(equalTo(2)))
  }

  @Test
  def supportsForExpression(): Unit = {
    // setup
    var count = 0

    // execute
    for (item <- List("bill", "bob")) count = count + 1

    // verify
    assertThat(count, is(equalTo(2)))
  }
}