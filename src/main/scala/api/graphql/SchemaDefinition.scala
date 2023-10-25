import sangria.schema.{ObjectType, fields, Schema}

object SchemaDefinition {
  val QueryType = ObjectType(
    "Query",
    fields[CustomerService, Unit](
      Field("customers", ListType(CustomerType), resolve = _.ctx.getAllCustomers)
    )
  )

  val MutationType = ObjectType(
    "Mutation",
    fields[CustomerService, Unit](
      Field("createCustomer", CustomerType, arguments = List(
        Argument("id", StringType),
        Argument("firstName", StringType),
        Argument("lastName", StringType),
        Argument("email", StringType)
      ), resolve = { ctx =>
        val id = ctx.arg[String]("id")
        val firstName = ctx.arg[String]("firstName")
        val lastName = ctx.arg[String]("lastName")
        val email = ctx.arg[String]("email")
        ctx.ctx.createCustomer(Customer(id, firstName, lastName, email))
      })
    )
  )

  val schema: Schema[CustomerService, Unit] = Schema(QueryType, Some(MutationType))
}
