package org.webfabric.rest

import java.io.{OutputStreamWriter, OutputStream}

class Response(val output:OutputStream){
  def write(value:String):Response = {
    var streamWriter = new OutputStreamWriter(output)
    streamWriter.write(value)
    streamWriter.flush
    this
  }
}