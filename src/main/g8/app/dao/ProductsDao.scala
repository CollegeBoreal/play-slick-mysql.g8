/*
 * DAO Implementation for Products Table.
 * If a repository pattern is desired instead, it can be built on top of many of the same components.
 */

package dao

import javax.inject.{Inject, Singleton}
import models.Product
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.time.LocalDateTime
import java.sql.Timestamp

import scala.concurrent.{ExecutionContext, Future}

trait ProductsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._
  import slick.lifted.ProvenShape

  /*
   */
  class ProductTable(tag: Tag) extends Table[Product](tag, "products") {

    import ProductTable._

    // scalastyle:off magic.number
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def sku: Rep[String] = column[String]("sku", O.Length(31, varying = true))
    def name: Rep[String] = column[String]("name", O.Length(127, varying = true))
    def description: Rep[String] = column[String]("description", O.Length(511, varying = true))
    def updated: Rep[LocalDateTime] = column[LocalDateTime]("updated")
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Product] =
      (id.?, sku, name, description, updated).mapTo[Product]
    // scalastyle: on method.name

  }

  object ProductTable {
    implicit val localDateTimeColumnType: BaseColumnType[LocalDateTime] =
      MappedColumnType.base[LocalDateTime, Timestamp](
        Timestamp.valueOf,
        _.toLocalDateTime
      )
  }
}

@Singleton()
class ProductsDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends ProductsComponent
    with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  val products = TableQuery[ProductTable]

  def getAll: Future[Seq[Product]] = db.run(products.result)
}
