package org.webfabric.stringtemplate

import java.net.URL
import org.webfabric.io.Url
import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
import java.io.{InputStreamReader, BufferedReader, IOException}
import java.lang.String

class UrlStringTemplateGroup(name: String, baseUrl: URL) extends StringTemplateGroup(name, baseUrl.toString) {
  override def loadTemplate(name: String, filename: String): StringTemplate = {
    try {
      val url = Url(filename)
      return loadTemplate(name, new BufferedReader(url.reader))
    } catch {
      case ex: IOException => return null;
    }
  }
}