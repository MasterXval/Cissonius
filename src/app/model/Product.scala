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

case class Product(id: Pk[Long], name: String)

object Product {

  val simple = {
    get[Pk[Long]]("id") ~
      get[String]("name") map {
      case id~name => Product(id, name)
    }
  }

  def findAll(): Seq[Product] = {
    DB.withConnection { implicit connection =>
      SQL("select * from product").as(Product.simple *)
    }
  }

  def create(product: Product): Unit = DB.withConnection { implicit connection =>
    SQL("insert into product(name) values ({name})").on(
      'name -> product.name
    ).executeUpdate()
  }

}