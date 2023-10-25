import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CustomerServiceSpec extends AnyFlatSpec with Matchers {
  "A CustomerService" should "be able to create and retrieve customers" in {
    val service = new CustomerService
    val customer = Customer("1", "John", "Doe", "john.doe@example.com")
    service.createCustomer(customer)
    service.getAllCustomers should contain (customer)
  }
}
