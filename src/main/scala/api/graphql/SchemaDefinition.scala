import sangria.schema._
import sangria.macros.derive._

object SchemaDefinition {

  val CustomerType: ObjectType[Unit, Customer] = deriveObjectType[Unit, Customer]()
  
  val QueryType: ObjectType[CustomerService, Unit] = ObjectType(
    "Query",
    fields[CustomerService, Unit](
      Field(
        name = "customers",
        fieldType = ListType(CustomerType),
        resolve = _.ctx.getAllCustomers
      )
    )
  )
  
  val IdArg = Argument("id", StringType)
  val FirstNameArg = Argument("firstName", StringType)
  val LastNameArg = Argument("lastName", StringType)
  val EmailArg = Argument("email", StringType)
  
  val MutationType: ObjectType[CustomerService, Unit] = ObjectType(
    "Mutation",
    fields[CustomerService, Unit](
      Field(
        name = "createCustomer",
        fieldType = CustomerType,
        arguments = IdArg :: FirstNameArg :: LastNameArg :: EmailArg :: Nil,
        resolve = { ctx =>
          val id = ctx.arg(IdArg)
          val firstName = ctx.arg(FirstNameArg)
          val lastName = ctx.arg(LastNameArg)
          val email = ctx.arg(EmailArg)
          ctx.ctx.createCustomer(Customer(id, firstName, lastName, email))
        }
      )
    )
  )
  
  val schema: Schema[CustomerService, Unit] = Schema(QueryType, Some(MutationType))
}
