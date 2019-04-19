package common

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.{Application, Mode}
import play.api.db.DBApi
import play.api.db.evolutions.Evolutions
import play.api.inject.guice.GuiceApplicationBuilder

abstract class SlickInMemorySpec
    extends PlaySpec
    with GuiceOneAppPerSuite
    with BeforeAndAfter {
  implicit override lazy val app: Application = {
    //val dbName = s"play-test-${scala.util.Random.nextInt()}"
    val dbName = "playdb-test"
    val dbUrl = s"jdbc:mysql://localhost/$dbName?useSSL=false"

    new GuiceApplicationBuilder()
      .configure(
        Map(
          "slick.dbs.default" -> Map(
            "profile" -> "slick.jdbc.MySQLProfile$",
            "db" -> Map(
              "driver" -> "com.mysql.cj.jdbc.Driver",
              "url" -> dbUrl,
              "user" -> "root",
              "password" -> "password"
            )
          )
        ))
      .in(Mode.Test)
      .build
  }

  before {
    val db = app.injector.instanceOf[DBApi]
    Evolutions.applyEvolutions(db.database("default"))
  }

  after {
    val db = app.injector.instanceOf[DBApi]
    Evolutions.cleanupEvolutions(db.database("default"))
  }
}
