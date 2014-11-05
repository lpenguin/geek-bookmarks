import javax.servlet.ServletContext

import com.mongodb.casbah.Imports._
import me.lilacpenguin.geekbookmarks.{HtmlServlet, ApiServlet}
import org.scalatra.LifeCycle

class ScalatraBootstrap extends LifeCycle {
  val mongoClient = MongoClient()
  override def init(context: ServletContext) {
    // As you can see, there's not much to do in order to get MongoDb working with Scalatra.
    // We're connecting with default settings - localhost on port 27017 - by calling MongoClient() with no arguments.
    val records = mongoClient("geekbm")("records")
    // pass a reference to the Mongo collection into your servlet when you mount it at application start:
    context.mount(new ApiServlet(records), "/api/*")
    context.mount(new HtmlServlet(records), "/*")
  }
  override def destroy(context: ServletContext) {
    //Foo bar
    mongoClient.close
  }
}
