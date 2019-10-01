package infra.doobie.model

import com.typesafe.config.ConfigFactory

object DbConfig {
  val config = ConfigFactory.load("database")
  lazy val driver = config.getString("database.driver")
  lazy val url =  config.getString("database.url")
  lazy val user =  config.getString("database.user")
  lazy val password =  config.getString("database.password")
}
