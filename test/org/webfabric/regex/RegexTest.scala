package org.webfabric.regex

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._

class RegexTest{
  @Test
  def canMatchPath() {
    val regex = new Regex("a(b)")
    assertThat(regex.isMatch("ab"), is(true))
  }

  @Test
  def canFindMatches() {
    val regex = new Regex("a(b)")
    assertThat(regex.matches("ab").head.groups.size, is(2))
  }

  @Test
  def canReplace() {
    val regex = new Regex("text")
    assertThat(regex.matches("some text").replace(matched => "foo"), is("some foo"))
  }

  @Test
  def canReplaceNonMatchedAsWell() {
    val regex = new Regex("text")
    assertThat(regex.matches("some text").replace(noneMatched => "another ", matched => "foo"), is("another foo"))
  }
}