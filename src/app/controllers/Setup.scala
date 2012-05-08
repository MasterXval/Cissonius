package controllers

import play.api.mvc.{Action, Controller}
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.{BasicResponseHandler, DefaultHttpClient}
import org.apache.http.entity.StringEntity
import org.apache.http.client.methods.{HttpPut, HttpPost, HttpGet}
import com.mongodb.casbah.Imports._


/**
 * Created with IntelliJ IDEA.
 * User: rgriffiths
 * Date: 05/05/12
 * Time: 07:46
 * To change this template use File | Settings | File Templates.
 */

object Setup  extends Controller {
  def setup = Action {

    configureIndex

    Ok(views.html.setup())
  }

  def configureIndex = {
    val  jsonString = """
{
  "type": "mongodb",
  "mongodb": {
    "db": "casbah_test",
    "host": "localhost",
    "port": 27017,
    "collection": "test_data",
    "gridfs" : false
  },
  "index": {
    "name": "product",
    "type": "product"
  }
}
      """

    val httpclient: HttpClient = new DefaultHttpClient
    val httpput = new HttpPut ("http://localhost:9200/_river/mongodb/_meta")
    httpput.setHeader("Content-Type", "application/json")
    httpput.setEntity(new StringEntity(jsonString,"UTF-8"))
    val responseHandler = new BasicResponseHandler
    val responseBody = httpclient.execute(httpput, responseHandler)
    println(responseBody)
  }

}
