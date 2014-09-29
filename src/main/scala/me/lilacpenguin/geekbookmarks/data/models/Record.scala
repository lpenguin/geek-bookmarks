package me.lilacpenguin.geekbookmarks.data.models

import java.util.Date

import com.mongodb.casbah.{MongoConnection, MongoCollection}
import com.novus.salat.annotations._
import com.novus.salat.dao.SalatDAO
import org.bson.types.ObjectId

import java.sql.Timestamp
/**
 * Created by nikita on 17.08.14.
 */

case class Record(
             @Key("_id") id: ObjectId,
             name:String,
             description:String,
             url:String,
             faviconUrl:String,
             tags:List[String],
             addedAt:Date = null
) {

  def this(name:String, description:String, url:String, tags:List[String], faviconUrl: String) = {
    this(new ObjectId(), name, description, url, faviconUrl, tags, new Date())
  }
}

class RecordDAO(collection:MongoCollection) extends SalatDAO[Record, ObjectId](collection)


