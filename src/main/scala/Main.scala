import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import api.routes.GraphQLRoutes
import domain.services.{CustomerService, CustomerServiceImpl}
import scala.io.StdIn

object Main extends App with GraphQLRoutes {
  
  implicit val system: ActorSystem = ActorSystem("trueml-backend-scala")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  
  override val customerService: CustomerService = new CustomerServiceImpl()

  val route = graphqlRoute

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
