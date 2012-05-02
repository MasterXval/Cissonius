package model


import anorm._
import anorm.SqlParser._
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._


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

  def create(product: Product) : Boolean =  {
    val mongoConn = MongoConnection()
    val mongoDB = mongoConn("casbah_test")
    val mongoColl = mongoConn("casbah_test")("test_data")

    val dbProduct = grater[Product].asDBObject(product)
    mongoColl.save(dbProduct)
    true
  }

  def findAll(): Iterator[Product] = {
    val mongoConn = MongoConnection()
    val mongoDB = mongoConn("casbah_test")
    val mongoColl = mongoConn("casbah_test")("test_data")

    mongoColl.find().map(x => grater[Product].asObject(x))
  }

}