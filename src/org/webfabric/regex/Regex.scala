package org.webfabric.regex

import java.util.regex.{Pattern, Matcher}

class Regex(value: String) {
  val pattern: Pattern = Pattern.compile(value)

  def matches(text: String): MatchIterable = new MatchIterable(text, pattern.matcher(text))

  def isMatch(text: String):Boolean = pattern.matcher(text).matches

  override def toString = pattern.pattern
}
