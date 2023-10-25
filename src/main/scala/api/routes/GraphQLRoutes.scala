package api.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import api.graphql.GraphQLExecutor
import domain.services.CustomerService
import spray.json.{JsObject, JsString, JsValue}

trait GraphQLRoutes {

  def customerService: CustomerService

  val graphqlRoute: Route = path("graphql") {
    post {
      entity(as[JsValue]) { requestJson =>
        val JsObject(fields) = requestJson
        val JsString(query) = fields("query")
        val operation = fields.get("operationName").collect {
          case JsString(op) => op
        }
        val vars = fields.get("variables").getOrElse(JsObject.empty).asJsObject
        
        GraphQLExecutor.executeQuery(query, operation, vars)(customerService)
      }
    }
  }
}
