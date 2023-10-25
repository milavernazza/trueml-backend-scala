import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import sangria.execution._
import sangria.parser.{QueryParser, SyntaxError}
import sangria.marshalling.sprayJson._
import spray.json._

object GraphQLExecutor {
  def executeQuery(query: String, operation: Option[String], vars: JsObject)(implicit service: CustomerService): Route = {
    QueryParser.parse(query) match {
      case Success(queryAst) =>
        complete(
          Executor.execute(
            schema = SchemaDefinition.schema,
            queryAst = queryAst,
            userContext = service,
            operationName = operation,
            variables = vars
          ).map(OK -> _)
           .recover {
             case error: QueryAnalysisError => BadRequest -> error.resolveError
             case error: ErrorWithResolver => InternalServerError -> error.resolveError
           }
        )
      case Failure(error: SyntaxError) =>
        complete(BadRequest, JsObject("error" -> JsString(error.getMessage)))
      case Failure(error) =>
        complete(InternalServerError, JsObject("error" -> JsString(error.getMessage)))
    }
  }
}
