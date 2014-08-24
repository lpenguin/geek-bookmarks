import me.lilacpenguin.geekbookmarks._
import me.lilacpenguin.geekbookmarks.data.DatabaseInit
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle with DatabaseInit{
  override def init(context: ServletContext) {
    configureDb()
    context.mount(new MainServlet, "/*")
  }

  override def destroy(context:ServletContext): Unit ={
    closeDbConnection()
  }
}
