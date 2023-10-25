import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import api.routes.GraphQLRoutes
import domain.services.{CustomerService, CustomerServiceImpl}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GraphQLSpec extends AnyFlatSpec with Matchers with ScalatestRouteTest with GraphQLRoutes {

  override val customerService: CustomerService = new CustomerServiceImpl()

  "The GraphQL endpoint" should "handle queries and mutations" in {

    // Test query
    Post("/graphql", """{"query": "{ customers { id, firstName } }"}""") ~> graphqlRoute ~> check {
      status shouldEqual StatusCodes.OK
      // ... other checks
    }

    // Test mutation
    Post("/graphql", """{"query": "mutation { createCustomer(id: \"1\", firstName: \"John\", lastName: \"Doe\", email: \"john.doe@example.com\") { id, firstName } }"}""") ~> graphqlRoute ~> check {
      status shouldEqual StatusCodes.OK
      // ... other checks
    }
  }
}
