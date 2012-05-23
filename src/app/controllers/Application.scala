package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import anorm.NotAssigned

import com.codahale.jerkson.Json
import com.codahale.jerkson.Json._

import model.Product
import play.api.Play.current
import play.api.libs.json.{JsValue, JsObject, JsArray}
import play.api.{Logger, Play}

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

  def google2Product(item: JsObject): Product  = {

    Logger.info("Inserting:" + (item \ "product" \ "title"));
    Product(NotAssigned,
      (item \ "product" \ "title").as[String],
      (item \ "product" \ "description").as[String],
      1,
      1,
      ((item \ "product" \ "images").as[List[JsObject]].head \ "link").as[String])
  }

  def jsonImporter() = Action(parse.json) {

    request =>
    (request.body \ "items" ).as[List[JsObject]].map(item  =>
      Product.create(google2Product(item)))
    Ok("Hello ")
  }
}