/*
 * DAO Implementation for Products Table.
 * If a repository pattern is desired instead, it can be built on top of many of the same components.
 */

package dao

import javax.inject.{Inject, Singleton}
import models.Product
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait ProductsComponent {
  self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class ProductTable(tag: Tag) extends Table[Product](tag, "products") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    // scalastyle:off magic.number
    def sku: Rep[String] = column[String]("sku", O.Length(31, varying = true))
    def name: Rep[String] =
      column[String]("name", O.Length(127, varying = true))
    def description: Rep[String] =
      column[String]("description", O.Length(511, varying = true))
    def updated: Rep[java.time.LocalDateTime] =
      column[java.time.LocalDateTime]("updated")
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Product] =
      (sku, name, description, updated, id.?).mapTo[Product]
    // scalastyle: on method.name

    implicit val localDateTimeColumnType
      : BaseColumnType[java.time.LocalDateTime] =
      MappedColumnType.base[java.time.LocalDateTime, java.sql.Timestamp](
        java.sql.Timestamp.valueOf,
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
