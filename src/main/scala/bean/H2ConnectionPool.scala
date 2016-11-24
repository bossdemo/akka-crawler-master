package bean

import tables.Tables.profile.api._

/**
  * Created by tongtao.zhu on 2016/11/24.
  */
object H2ConnectionPool {

  def getConnection(dbName: String): Database = {
    val url = "jdbc:h2:file:e:/db/%s;INIT=runscript from 'src/main/resources/task.sql'".format(dbName)
    val db = Database.forURL(url, driver = "org.h2.Driver")
    db
  }
}
