package me.lilacpenguin.geekbookmarks

import com.mongodb.casbah.Imports._
import me.lilacpenguin.geekbookmarks.data.models.{RecordDAO, Record}
import org.json4s._
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.{Logger, LoggerFactory}
import org.json4s.JsonDSL._
import me.lilacpenguin.geekbookmarks.data.models.Record
import org.json4s.JsonAST.{JValue, JObject}

class ApiServlet(recordsCollection: MongoCollection) extends GeekbookmarksStack with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  val logger = LoggerFactory.getLogger(getClass)
  val recordsDAO = new RecordDAO(recordsCollection)
  
  def recordToJObject(r: Option[Record]):JValue = {

    r match {
      case Some(record) => Extraction decompose record
      case None => JNull
    }
  }
  
  before() {
    logger.info(request.uri+" with body: \n"+request.body)
    contentType = formats("json")
  }

  get("/records/?"){
    val records = recordsDAO.find(MongoDBObject()).toList
    ("status" -> "ok") ~ ("records" -> Extraction.decompose(records))
  }

  post("/addLink"){
    val json = parse(request.body)
    recordsDAO.insert(json.extract[Record])
    "status" -> "ok"
  }

  post("/findUrl"){
    val json = parse(request.body)
    val url = (json \\ "url").extract[String]
    val r = recordsDAO.findOne(MongoDBObject("url" -> url))
    ("result" -> recordToJObject(r)) ~ ("status" -> "ok")
  }

  get("/tags"){
    val mapFunction = "" +
      "function(){ \n"+
      "    this.tags.map(function(tag, index){ \n"+
      "        emit(tag, 1); \n" +
      "    }); \n" +
      "}\n"
      
	  val reduceFunction = "function(tag, values){ \n" +
          " return Array.sum(values); \n" +
          " }"
    val result = recordsCollection.mapReduce(mapFunction, reduceFunction, MapReduceInlineOutput)
    

    ("result" -> result.map(o => {
      ("tag" -> o.getAs[String]("_id")) ~ ("count" -> o.getAs[String]("value"))
    }).toList) ~ ("status" -> "ok")
    
  }

  error {
    case e =>
      logger.error("error-marker", e)
  }
}
