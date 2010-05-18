package org.webfabric.regex

import java.util.regex.Matcher
import org.webfabric.collections.{List}

class MatchIterator(matcher: Matcher) extends java.util.Iterator[Match] {
    matcher.reset
    var currentMatch: Option[Match] = None

    def next(): Match = currentMatch match {
      case Some(result) => {
        currentMatch = None
        result
      }
      case None => if (hasNext) next else throw new NoSuchElementException
    }

    def hasNext = currentMatch match {
      case None => matcher.find match {
        case true => {
          currentMatch = Some(new Match(groups))
          true
        }
        case false => false
      }
      case _ => true
    }

    def remove = throw new UnsupportedOperationException

    def groups = {
      val groups = List[Group]()
      for (i <- 0 to matcher.groupCount) groups.add(new Group(matcher.group(i), matcher.start(i), matcher.end(i)))
      groups
    }

  }