package org.webfabric.regex

import java.util.regex.Matcher
import org.webfabric.collections.Iterable

class MatchIterable(text:String, matcher: Matcher) extends Iterable[Match] {
    def iterator = {
      new MatchIterator(matcher)
    }

    def replace(matched: (Match) => String): String = replace(notMatched => notMatched, matched)

    def replace(notMatched: (String) => String, matched: (Match) => String) = {
      val builder = new StringBuilder
      var position = 0;
      foreach(m => {
        var before = text.substring(position, m.start)
        if (before.length > 0) builder.append(notMatched(before))
        builder.append(matched(m))
        position = m.end
      })
      var after = text.substring(position)
      if (after.length > 0) builder.append(notMatched(after))
      builder.toString
    }
  }
