package org.webfabric.http


import java.util.Calendar
import javax.servlet.http.HttpServletResponse

class CachePolicy(var seconds:Int){
  def noCache = {
    seconds = 0
  }

  def writeTo(response: HttpServletResponse){
    val calendar = Calendar.getInstance
    response.setDateHeader("Date", calendar.getTimeInMillis)
    seconds match {
      case 0 => {
        response.setHeader("Cache-Control", "no-cache")
        response.setDateHeader("Expires", 0)
      }
      case _ => {
        response.setHeader("Cache-Control", "public, max-age=" + seconds)
        calendar.add(Calendar.SECOND, seconds)
        response.setDateHeader("Expires", calendar.getTimeInMillis)
      }
    }
  }
}