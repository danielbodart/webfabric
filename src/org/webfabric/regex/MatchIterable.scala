package org.webfabric.regex

import java.util.regex.Matcher
import org.webfabric.collections.Iterable

class MatchIterable(matcher: Matcher) extends Iterable[Match] {
    def iterator = {
      new MatchIterator(matcher)
    }

    def replace(replacer: (Match) => String): String = {
      val buffer = new StringBuffer
      foreach(aMatch => {
        matcher.appendReplacement(buffer, replacer(aMatch))
      })
      matcher.appendTail(buffer)
      buffer.toString
    }
  }
