package org.webfabric.properties

import java.util.Properties
import org.webfabric.io.Url
import java.io.OutputStream
import java.net.HttpURLConnection

class WebProperties(url:Url) extends Properties{
  this.load(url.inputStream)

  def flush {
    val urlConnection:HttpURLConnection = url.openConnection
    urlConnection.setDoOutput(true);
    urlConnection.setRequestMethod("PUT");
    val outputStream: OutputStream = urlConnection.getOutputStream
    this.store(outputStream, null)
    outputStream.close
    Console.println(urlConnection.getResponseCode + " " + urlConnection.getResponseMessage)
  }
}