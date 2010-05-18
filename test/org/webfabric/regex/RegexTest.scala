package org.webfabric.regex

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._

class RegexTest{
  @Test
  def canFindMatches() {
    val regex = new Regex("""a(b)""")
    assertThat(regex.matches("ab").head.groups.size, is(2))
  }

  @Test
  def canMatchPath() {
    val regex = new Regex("""a(b)""")
    assertThat(regex.isMatch("ab"), is(true))
  }
}