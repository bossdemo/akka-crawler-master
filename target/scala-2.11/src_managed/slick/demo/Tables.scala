package demo
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Task.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Task
   *  @param url Database column URL SqlType(VARCHAR)
   *  @param source Database column SOURCE SqlType(VARCHAR)
   *  @param `type` Database column TYPE SqlType(VARCHAR)
   *  @param status Database column STATUS SqlType(VARCHAR)
   *  @param prior Database column PRIOR SqlType(VARCHAR) */
  case class TaskRow(url: String, source: String, `type`: String, status: String, prior: String)
  /** GetResult implicit for fetching TaskRow objects using plain SQL queries */
  implicit def GetResultTaskRow(implicit e0: GR[String]): GR[TaskRow] = GR{
    prs => import prs._
    TaskRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table TASK. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class Task(_tableTag: Tag) extends Table[TaskRow](_tableTag, "TASK") {
    def * = (url, source, `type`, status, prior) <> (TaskRow.tupled, TaskRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(url), Rep.Some(source), Rep.Some(`type`), Rep.Some(status), Rep.Some(prior)).shaped.<>({r=>import r._; _1.map(_=> TaskRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column URL SqlType(VARCHAR) */
    val url: Rep[String] = column[String]("URL")
    /** Database column SOURCE SqlType(VARCHAR) */
    val source: Rep[String] = column[String]("SOURCE")
    /** Database column TYPE SqlType(VARCHAR)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("TYPE")
    /** Database column STATUS SqlType(VARCHAR) */
    val status: Rep[String] = column[String]("STATUS")
    /** Database column PRIOR SqlType(VARCHAR) */
    val prior: Rep[String] = column[String]("PRIOR")
  }
  /** Collection-like TableQuery object for table Task */
  lazy val Task = new TableQuery(tag => new Task(tag))
}
