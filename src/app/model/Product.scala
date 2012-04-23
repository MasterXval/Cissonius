package model

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

/**
 * Created with IntelliJ IDEA.
 * User: rgriffiths
 * Date: 22/04/12
 * Time: 08:24
 * To change this template use File | Settings | File Templates.
 */

case class Product(id: Pk[Long],
                   name: String,
                   description: String,
                   price : Long,
                   price_strikeout : Long,
                   image : String
                   )

object Product {

  val complete = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[Long]("price") ~
    get[Long]("price_strikeout") ~
    get[String]("image") map {
        case id~name~description~price~price_strikeout~image => Product(id, name, description, price, price_strikeout, image)
      }
  }

  def findAll(): Seq[Product] = {
    DB.withConnection { implicit connection =>
      SQL("select * from product").as(Product.complete *)
    }
  }

  def create(product: Product): Unit = DB.withConnection { implicit connection =>
    SQL("insert into product(name, description, price, price_strikeout, image) " +
      "values ({name},{description},{price},{price_strikeout},{image})").on(
      'name -> product.name,
      'description -> product.description,
      'price ->  product.price,
      'price_strikeout ->  product.price_strikeout,
      'image ->  product.image
    ).executeUpdate()
  }
}