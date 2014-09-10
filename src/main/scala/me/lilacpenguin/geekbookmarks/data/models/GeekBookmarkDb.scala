package me.lilacpenguin.geekbookmarks.data.models

import org.squeryl.dsl.CompositeKey2
import org.squeryl.{KeyedEntity, Schema}
import org.squeryl.PrimitiveTypeMode._

import java.sql.Timestamp
/**
 * Created by nikita on 17.08.14.
 */

class TagRecord(val tagId:Long, val recordId:Long) extends KeyedEntity[CompositeKey2[Long, Long]]{
  def id = compositeKey(tagId, recordId)
}

object GeekBookmarkDb extends Schema {
  val tags = table[Tag]
  val records = table[Record]

  val tagsRecords =
    manyToManyRelation(tags, records).
    via[TagRecord]((t,r,tr) => (r.id === tr.recordId, t.id === tr.tagId))

 }

class Tag(val id:Long, val name:String) extends KeyedEntity[Long]{
  def this(name:String) = this(0, name)
  lazy val records = GeekBookmarkDb.tagsRecords.left(this)
}

case class Record(
             id:Long = 0,
             name:String,
             description:String,
             url:String,
             faviconUrl:String,
             addedAt:Timestamp = null
) extends KeyedEntity[Long]{

  def this(name:String, description:String, url:String, faviconUrl: String) = {
    this(0, name, description, url, faviconUrl, null)
  }

  lazy val tags = GeekBookmarkDb.tagsRecords.right(this)
}


