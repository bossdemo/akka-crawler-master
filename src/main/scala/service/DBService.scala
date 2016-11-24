package service

import bean.CrawlerTaskRepository
import tables.Tables.TaskRow

/**
  * Created by tongtao.zhu on 2016/11/23.
  * 负责将task存储到数据库
  */
class DBService {
  var crawlerTaskRepository: CrawlerTaskRepository = _

  def create(crawlerTask: TaskRow) = {
    //在插入之前先查找是否已经存在于表中
    crawlerTaskRepository.find(crawlerTask)

    crawlerTaskRepository.create(crawlerTask)
  }

  def find(crawlerTask: TaskRow) = {

  }

  def update(crawlerTask: TaskRow) = {
    crawlerTaskRepository.update(crawlerTask)
  }
}
