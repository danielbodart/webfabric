package org.webfabric.sitemesh.v2

import com.opensymphony.module.sitemesh.HTMLPage
import java.util.{HashMap}
import java.util.Map.Entry
import org.webfabric.collections.Iterable
import java.lang.String
import org.webfabric.sitemesh.{TreeProperty}

object PageAdapter {
  def parse(page: HTMLPage):TreeProperty = {
    val root = new TreeProperty("root")
    root.getChild("head").asInstanceOf[TreeProperty].setValue(page.getHead)
    root.getChild("body").asInstanceOf[TreeProperty].setValue(page.getBody)

    Iterable.foreach(page.getProperties.entrySet, (e:Entry[_,_]) => {
      val compositeKey:String = e.getKey.toString
      var currentNode:TreeProperty = root
      compositeKey.split("\\.").foreach(key => {
        currentNode = currentNode.getChild(key).asInstanceOf[TreeProperty]
      })
      currentNode.setValue(e.getValue.toString)
    })
    root
  }
}