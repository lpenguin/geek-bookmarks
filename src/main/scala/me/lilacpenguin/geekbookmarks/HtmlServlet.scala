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

  get("/"){
    val items = from(records)(r => select(r)).toList
    jade("index", "items" -> items)
  }

  get("/tags/:tag"){
    val tagId = params("tag").toInt

    val items = from(records, tagsRecords)((r, tr)=>
      where(tr.tagId === tagId and r.id === tr.recordId)
      select r
    ).toList


    jade("index", "items" -> items)
  }
}
