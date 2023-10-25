class CustomerService {
  def getAllCustomers: List[Customer] = CustomerRepository.findAll()

  def createCustomer(customer: Customer): Customer = CustomerRepository.create(customer)
}
