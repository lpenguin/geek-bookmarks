package me.lilacpenguin.geekbookmarks

import com.mongodb.casbah.Imports._
import me.lilacpenguin.geekbookmarks.data.models.Record
import org.json4s.JsonAST.JArray
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.{Logger, LoggerFactory}
import org.json4s.JsonDSL._

class ApiServlet(records: MongoCollection) extends GeekbookmarksStack with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  val logger = LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
  }

//  get("/tags") {
//    from(tags)(t => select(t)).toList
//  }
//
//  get("/records"){
//    from(records)(r => select(r)).toList
//  }
//
//  get("/db"){
//    GeekBookmarkDb.create
//    ("result" -> "ok") ~ ("message" -> "db created")
//  }
//
//  def makeTag(name:String) = {
//    val tagQuery = tags where (t => t.name === name)
//
//    if (tagQuery.size >= 1) tagQuery.head
//    else tags insert new Tag(name)
//  }
//  get("/dropdb"){
//    GeekBookmarkDb.tagsRecords.deleteWhere(r => 1 === 1)
//    GeekBookmarkDb.tags.deleteWhere(r => 1 === 1)
//    GeekBookmarkDb.records.deleteWhere(r => 1 === 1)
//    GeekBookmarkDb.drop
//    ("result" -> "ok") ~ ("message" -> "db created")
//  }
  post("/addLink"){
    logger.info("addLink: {}", request.body)
    val json = parse(request.body)
    val record = json.extract[Record]


    "status" -> "ok"

  }

  error {
    case e =>
      logger.error("error-marker", e)
  }
}
