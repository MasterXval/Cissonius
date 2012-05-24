package model

import com.mongodb.casbah.Imports._
import com.novus.salat._
import play.api.Play
import play.api.Play.current

/**
 * Created with IntelliJ IDEA.
 * User: rgriffiths
 * Date: 24/05/12
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */

class MongoDBManager {

}

object MongoDBManager {
  def productCollection() = {
    val mongoDB = MongoConnection()(Play.configuration.getString("cissonius.mongodb.database").get)
    mongoDB("product")
  }
}