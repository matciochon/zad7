package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

@Singleton
class ProductsController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  var products = scala.collection.mutable.ListBuffer(
    Product("1", "Telewizor", 100),
    Product("2", "Pianino", 200),
    Product("3", "Laptop", 150)
  )

  def createProduct: Action[JsValue] = Action(parse.json) { request =>
    val productResult = request.body.validate[Product]
    productResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> "Bad product format"))
      },
      product => {
        products += product
        Created(Json.obj("message" -> "Product added"))
      }
    )
  }

  def listProducts: Action[AnyContent] = Action { _ =>
    Ok(Json.toJson(products))
  }

  def updateProduct(id: String): Action[JsValue] = Action(parse.json) { request =>
    val productResult = request.body.validate[Product]
    productResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> "Bad product format"))
      },
      product => {
        val i = products.indexWhere(_.id == id)
        if (i != -1) {
          products(i) = product.copy(id = id)
          Ok(Json.obj("message" -> "Product updated"))
        } else {
          NotFound(Json.obj("message" -> s"Product not found"))
        }
      }
    )
  }

  def deleteProduct(id: String): Action[AnyContent] = Action { _ =>
    val size = products.size
    products = products.filterNot(_.id == id)
    if (products.size < size) {
      Ok(Json.obj("message" -> "Product removed"))
    } else {
      NotFound(Json.obj("message" -> s"Product not found"))
    }
  }
}

case class Product(id: String, name: String, price: Int)

object Product {
  implicit val productFormat: OFormat[Product] = Json.format[Product]
}
