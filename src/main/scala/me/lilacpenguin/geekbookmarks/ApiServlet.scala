package me.lilacpenguin.geekbookmarks

import com.mongodb.casbah.Imports._
import me.lilacpenguin.geekbookmarks.data.models.{RecordDAO, Record}
import org.json4s.JsonAST.JArray
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.{Logger, LoggerFactory}
import org.json4s.JsonDSL._

class ApiServlet(records: MongoCollection) extends GeekbookmarksStack with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  val logger = LoggerFactory.getLogger(getClass)
  val recordsDAO = new RecordDAO(records)
  before() {
    contentType = formats("json")
  }

//  get("/tags") {
//    from(tags)(t => select(t)).toList
//  }
//
  get("/records"){
    recordsDAO.find(MongoDBObject.empty).toList
  }

  post("/addLink"){
    val json = parse(request.body)
    recordsDAO.insert(json.extract[Record])
    "status" -> "ok"
  }

  error {
    case e =>
      logger.error("error-marker", e)
  }
}
