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
                   image : String
                   )

object Product {

  def create(product: Product){

    val dbProduct = grater[Product].asDBObject(product)
    MongoDBManager.productCollection().save(dbProduct)
  }

  def findAll(): Iterator[Product] = {

    MongoDBManager.productCollection().find().map(x => grater[Product].asObject(x))
  }

}