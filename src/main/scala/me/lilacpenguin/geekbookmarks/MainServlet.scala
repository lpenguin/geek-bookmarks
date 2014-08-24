package me.lilacpenguin.geekbookmarks

import me.lilacpenguin.geekbookmarks.data.DatabaseSessionSupport
import me.lilacpenguin.geekbookmarks.data.models.{Record, GeekBookmarkDb, Tag}
import org.json4s.JsonAST.JArray
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory
import org.squeryl.PrimitiveTypeMode._
import org.json4s.JsonDSL._
import org.squeryl.Query
import GeekBookmarkDb._

class MainServlet extends GeekbookmarksStack with DatabaseSessionSupport with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  val logger = LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
  }

  get("/tags") {
    from(tags)(t => select(t)).toList
  }

  get("/records"){
    from(records)(r => select(r)).toList
  }

  get("/db"){
    GeekBookmarkDb.create
    ("result" -> "ok") ~ ("message" -> "db created")
  }

  def makeTag(name:String, tagQuery:Query[Tag]) =
    if(tagQuery.size >= 1) tagQuery.head
    else tags insert new Tag(name)

  get("/p"){
    val json = parse("""
      { "name": "fifr-record",
        "description": "fifr-description",
        "url": "http://ya.ru",
        "tags": ["fifr", "fufr", "fafr"]
      }
      """)

    val record = records insert json.extract[Record]

    val recordTags = (json \ "tags")
      .extract[List[String]]
      .map(name => makeTag(name, tags.where(t => t.name === name)))

    for(tag <- recordTags){
      record.tags.associate(tag)
    }

    "result" -> "ok"

  }
}
