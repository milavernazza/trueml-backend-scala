// src/main/scala/domain/services/CustomerService.scala
trait CustomerService {
  def getAllCustomers: List[Customer]
  def createCustomer(customer: Customer): Customer
}

class CustomerServiceImpl extends CustomerService {
  // assuming you have a repository for managing customers
  override def getAllCustomers: List[Customer] = CustomerRepository.findAll()
  override def createCustomer(customer: Customer): Customer = CustomerRepository.create(customer)
}
