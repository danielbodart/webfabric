package org.webfabric.servlet

import org.webfabric.io.{Url}
import org.webfabric.io.Url._
import javax.servlet.ServletContext

class WarRoot(url: Url)

object WarRoot {
  def apply(context: ServletContext):WarRoot = {
    val url = context.getResource("/WEB-INF/web.xml").parent.parent
    return new WarRoot(url);
  }
}
