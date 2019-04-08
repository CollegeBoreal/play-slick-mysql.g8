package controllers

import dao.ProductsDao
import javax.inject.{Inject, Singleton}

import models.Product
import play.api.libs.json.{Format, Json, OFormat}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class ProductController @Inject()(
    cc: ControllerComponents,
    productsDao: ProductsDao)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  /*
  Note: JSon custom automated mapping using OFormat (i.e. java.time.LocalDateTime
   */
  implicit val fmt: OFormat[Product] = Json.format[Product]

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { products <- productsDao.getAll } yield Ok(Json.toJson(products))
  }
}
