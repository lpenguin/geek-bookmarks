package me.lilacpenguin.geekbookmarks.data.models

import java.util.Date

import com.mongodb.casbah.{MongoCollection}
import com.novus.salat.annotations._
import com.novus.salat.dao.SalatDAO
import org.bson.types.ObjectId
import com.novus.salat.global._

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
             addedAt:Date = new Date()
) {
}

class RecordDAO(collection: MongoCollection) extends SalatDAO[Record, ObjectId](collection)


