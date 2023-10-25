object CustomerRepository {
  private var customers: List[Customer] = List()

  def findAll(): List[Customer] = customers

  def create(customer: Customer): Customer = {
    customers = customers :+ customer
    customer
  }
}
