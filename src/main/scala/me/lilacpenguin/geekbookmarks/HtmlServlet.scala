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
    jade("index", "items" -> recordsDAO.find(MongoDBObject.empty).sort(orderBy = MongoDBObject("addedAt" -> -1)).toList)
  }

  get("/tags/:tag"){
    val tags = params("tag") :: Nil
    val items = recordsDAO.find("tags" $in tags).sort(orderBy = MongoDBObject("addedAt" -> -1)).toList
    jade("index", "items" -> items)
  }
  
  get("/records/remove/:ids"){
    val id = params("ids")
    recordsDAO.removeById(new ObjectId(id))
    val items = recordsDAO.find(MongoDBObject.empty).sort(orderBy = MongoDBObject("addedAt" -> -1)).toList
    jade("index", "items" -> items)
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
