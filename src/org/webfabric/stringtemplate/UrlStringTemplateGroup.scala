package org.webfabric.stringtemplate

import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
import java.io.{InputStreamReader, BufferedReader, IOException}
import java.lang.String
import java.net.URL

class UrlStringTemplateGroup(name: String, baseUrl: URL) extends StringTemplateGroup(name, baseUrl.toString) {
  override def loadTemplate(name: String, filename: String): StringTemplate = {
    try {
      val url = new URL(filename)
      val reader = new BufferedReader(new InputStreamReader(url.openStream))
      return loadTemplate(name, reader)
    } catch {
      case ex: IOException => return null;
    }
  }
}