package org.webfabric.rest


import org.webfabric.collections.{List, Iterable}
import org.webfabric.regex.{Regex}
import java.util.regex.Pattern

class UriTemplate(template: String) extends Matcher[String] {
  def isMatch(uri: String): Boolean = templateRegex.isMatch(uri)

  def extract(uri: String): PathParameters = {
    var values = templateRegex.matches(uri).map(m => m.groups.get(1).value)
    names.zip(values).foldLeft(PathParameters(), (parameters: PathParameters, pair: (String, String)) => {
      parameters.add(pair._1, pair._2)
      parameters
    })
  }

  def generate(parameters: PathParameters): String = {
    matches.replace(m => parameters.getValue(m.groups.get(1).value))
  }

  lazy val matches = UriTemplate.pathParameters.matches(template)
  lazy val names = matches.map(m => m.groups.get(1).value)
  lazy val templateRegex = new Regex(matches.replace(notMatched => Pattern.quote(notMatched), matched => """([^/]+)"""))
}

object UriTemplate {
  val pathParameters = new Regex("""\{(\w+)(?:\:(\w+))?\}""")
}
