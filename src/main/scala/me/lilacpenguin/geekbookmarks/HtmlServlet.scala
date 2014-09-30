package me.lilacpenguin.geekbookmarks

import com.mongodb.casbah.Imports._
import me.lilacpenguin.geekbookmarks.data.models.RecordDAO


/**
 * Created by nikita on 03.09.14.
 */
class HtmlServlet(records: MongoCollection) extends GeekbookmarksStack {
  val recordsDAO = new RecordDAO(records)

  before(){
    contentType="text/html"
  }

  get("/"){
    jade("index", "items" -> recordsDAO.find(MongoDBObject.empty).toList)
  }
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
