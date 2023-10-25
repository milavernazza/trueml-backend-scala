import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import sangria.execution.Executor
import sangria.parser.QueryParser
import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("trueml-backend-scala")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val customerService = new CustomerServiceImpl()

    val route: Route = path("graphql") {
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

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
