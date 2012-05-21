package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import anorm.NotAssigned

import com.codahale.jerkson.Json
import model.Product
import play.api.Play
import play.api.Play.current

object Application extends Controller {

  val productForm = Form(
    tuple(
      "name" -> text,
      "description" -> text,
      "price" -> number,
      "price_strikeout" -> number,
      "image" -> text
    )
  )

  val googleApiKey =  Play.configuration.getString("cissonius.google.shooping.apikey").get

  def index = Action {
    Ok(views.html.index(productForm))
  }

  def list = Action {
    Ok(views.html.list())
  }

  def addProduct() = Action { implicit request =>
    productForm.bindFromRequest.fold(
    errors => BadRequest,
    {
      case (name, description, price, price_strikeout, image) =>
        Product.create(Product(NotAssigned, name, description, price, price_strikeout, image))
        Redirect(routes.Application.index())
    }
    )
  }

  def listProducts() = Action {
    val products = Product.findAll()
    val json = Json.generate(products)
    Ok(json).as("application/json")
  }

  def importer() = Action {

    Ok(views.html.importer(googleApiKey))
  }
}