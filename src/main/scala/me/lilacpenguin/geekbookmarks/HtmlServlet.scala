package me.lilacpenguin.geekbookmarks

import com.mongodb.casbah.Imports._


/**
 * Created by nikita on 03.09.14.
 */
class HtmlServlet(mongoColl: MongoCollection) extends GeekbookmarksStack {
  before(){
    contentType="text/html"
  }

//  get("/"){
//    val items = from(records)(r => select(r)).toList
//    jade("index", "items" -> items)
//  }
//
//  get("/tags/:tag"){
//    val tagId = params("tag").toInt
//
//    val items = from(records, tagsRecords)((r, tr)=>
//      where(tr.tagId === tagId and r.id === tr.recordId)
//      select r
//    ).toList
//
//
//    jade("index", "items" -> items)
//  }
}
