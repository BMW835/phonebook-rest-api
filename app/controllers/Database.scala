package controllers

import models._
import scala.slick.driver.PostgresDriver.simple._

class Phonebook(tag: Tag) extends Table[(Long, String, String)](tag, "phonebook") {
  def id: Column[Long] = column[Long]("id")
  def name: Column[String] = column[String]("name")
  def phone: Column[String] = column[String]("phone")
  def * = (id, name, phone)
}

trait Database {
  def add(pf: PhoneForm): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.map(c => (c.name, c.phone)) += (pf.name, pf.phone)
    }
  }

  def all(): List[Phone] = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.list.map { case (id, name, phone) => Phone(id, name, phone) }
    }
  }

  def mod(id: Long, pf: PhoneForm): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

          phones
          .filter(_.id === id)
          .map(c => (c.name, c.phone))
          .update((pf.name, pf.phone))
    }
  }

  def del(id: Long): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

          phones
          .filter(_.id === id)
          .delete
    }
  }

  def nameLike(sub: String): List[Phone] = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.filter(_.name like s"%$sub%").list.map { case (id, name, phone) => Phone(id, name, phone) }
    }
  }

  def phoneLike(sub: String): List[Phone] = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.filter(_.phone like s"%$sub%").list.map { case (id, name, phone) => Phone(id, name, phone) }
    }
  }

}

object DB extends Database