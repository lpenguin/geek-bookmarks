package me.lilacpenguin.geekbookmarks

import me.lilacpenguin.geekbookmarks.data.DatabaseSessionSupport
import me.lilacpenguin.geekbookmarks.data.models.GeekBookmarkDb._
import org.squeryl.PrimitiveTypeMode._

/**
 * Created by nikita on 03.09.14.
 */
class HtmlServlet extends GeekbookmarksStack with DatabaseSessionSupport{
  before(){
    contentType="text/html"
  }

  get("/hello"){
    jade("fifr")
  }

  get("/"){
    val items = from(records)(r => select(r)).toList
    jade("index", "items" -> items)
  }

  get("/tags/:tag"){
    val items = from(records)(r => select(r)).toList
    jade("index", "items" -> items)
  }
}
