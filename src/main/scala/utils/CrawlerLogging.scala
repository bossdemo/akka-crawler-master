package utils

import org.slf4j.{LoggerFactory, Logger}

/**
  * Created by tongtao.zhu on 2016/11/25.
  */
trait CrawlerLogging {
  val loggerName = this.getClass.getName
  lazy val logger = LoggerFactory.getLogger(loggerName)

  protected var logIdent: String = null
  private def msgWithLogIdent(msg: String) =
    if(logIdent == null) msg else logIdent + msg

  def info(msg: => String): Unit = {
    if (logger.isInfoEnabled())
      logger.info(msgWithLogIdent(msg))
  }
  def info(e: => Throwable): Any = {
    if (logger.isInfoEnabled())
      logger.info(logIdent,e)
  }
  def info(msg: => String,e: => Throwable) = {
    if (logger.isInfoEnabled())
      logger.info(msgWithLogIdent(msg),e)
  }

  def debug(msg: => String): Unit = {
    if (logger.isDebugEnabled())
      logger.debug(msgWithLogIdent(msg))
  }
  def debug(e: => Throwable): Any = {
    if (logger.isDebugEnabled())
      logger.debug(logIdent,e)
  }
  def debug(msg: => String, e: => Throwable) = {
    if (logger.isDebugEnabled())
      logger.debug(msgWithLogIdent(msg),e)
  }

  def warn(msg: => String): Unit = {
    logger.warn(msgWithLogIdent(msg))
  }
  def warn(e: => Throwable): Any = {
    logger.warn(logIdent,e)
  }
  def warn(msg: => String, e: => Throwable) = {
    logger.warn(msgWithLogIdent(msg),e)
  }

  def error(msg: => String): Unit = {
    logger.error(msgWithLogIdent(msg))
  }
  def error(e: => Throwable): Any = {
    logger.error(logIdent,e)
  }
  def error(msg: => String, e: => Throwable) = {
    logger.error(msgWithLogIdent(msg),e)
  }
}
