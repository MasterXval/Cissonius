package controllers

import play.api.data.Form
import play.api.data.Forms.{single, nonEmptyText}
import play.api.mvc.{Action, Controller}
import anorm.NotAssigned

import model.Product
import com.codahale.jerkson.Json

object Application extends Controller {
  val productForm = Form(
    single("name" -> nonEmptyText)
  )

  def index = Action {
    Ok(views.html.index(productForm))
  }

  def addProduct() = Action { implicit request =>
    productForm.bindFromRequest.fold(
    errors => BadRequest,
    {
      case (name) =>
        Product.create(Product(NotAssigned, name))
        Redirect(routes.Application.index())
    }
    )
  }

  def listProducts() = Action {
    val products = Product.findAll()

    val json = Json.generate(products)

    Ok(json).as("application/json")
  }
}