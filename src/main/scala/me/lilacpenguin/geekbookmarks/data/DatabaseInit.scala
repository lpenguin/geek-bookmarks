package me.lilacpenguin.geekbookmarks.data

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import org.squeryl.SessionFactory
import org.squeryl.Session
import org.squeryl.adapters.H2Adapter

/**
 * Created by nikita on 17.08.14.
 */
trait DatabaseInit {
  val logger = LoggerFactory.getLogger(getClass)

  val databaseUsername = "admin"
  val databasePassword = "admin123"
  val databaseConnection = "jdbc:mysql://localhost/geekbm"

  var cpds = new ComboPooledDataSource

  def configureDb() {
    logger.info("configureDb()")
    cpds.setDriverClass("com.mysql.jdbc.Driver")
    cpds.setJdbcUrl(databaseConnection)
    cpds.setUser(databaseUsername)
    cpds.setPassword(databasePassword)

    cpds.setMinPoolSize(1)
    cpds.setAcquireIncrement(1)
    cpds.setMaxPoolSize(50)

    SessionFactory.concreteFactory = Some(() => connection)

    def connection = {
      logger.info("Creating connection with c3po connection pool")
      Session.create(cpds.getConnection, new H2Adapter)
    }
  }

  def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close()
  }
}
