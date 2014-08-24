package me.lilacpenguin.geekbookmarks.data

import org.scalatra.ScalatraBase
import org.slf4j.LoggerFactory
import org.squeryl.{SessionFactory, Session}
/**
 * Created by nikita on 17.08.14.
 */
object DatabaseSessionSupport {
  val key = {
    val n = getClass.getName
    if(n.endsWith("$")) n.dropRight(1) else n
  }
}

trait DatabaseSessionSupport { this:ScalatraBase =>
  import DatabaseSessionSupport._

  def dbSession = request.get(key).orNull.asInstanceOf[Session]

  before() {
    request(key) = SessionFactory.newSession
    dbSession.bindToCurrentThread
  }

  after() {
    dbSession.close
    dbSession.unbindFromCurrentThread
  }

}
