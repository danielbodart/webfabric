package org.webfabric.regex

import org.webfabric.collections.List

case class Match(groups: List[Group]){
  def start = groups.head.start
  def end = groups.head.end
}
