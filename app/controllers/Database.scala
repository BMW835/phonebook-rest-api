package controllers

import models._

import scala.slick.driver.PostgresDriver.simple._
import java.sql.Timestamp
import java.time.LocalDateTime

class Phonebook(tag: Tag) extends Table[(Long, String, String, Timestamp)](tag, "phones") {
  def id: Column[Long] = column[Long]("id")
  def name: Column[String] = column[String]("name")
  def phone: Column[String] = column[String]("phone")
  def time: Column[Timestamp] = column[Timestamp]("time")
  def * = (id, name, phone, time)
}

trait Database {
  def add(pf: PhoneForm): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.map(c => (c.name, c.phone, c.time)) += (pf.name, pf.phone, Timestamp.valueOf(LocalDateTime.now()))
    }
  }

  def all(): List[Phone] = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.list.map { case (id, name, phone, time) => Phone(id, name, phone, time) }
    }
  }

  def mod(id: Long, pf: PhoneForm): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones
          .filter(_.id === id)
          .map(c => (c.name, c.phone, c.time))
          .update((pf.name, pf.phone, Timestamp.valueOf(LocalDateTime.now())))
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

        phones.filter(_.name like s"%$sub%").list.map { case (id, name, phone, time) => Phone(id, name, phone, time) }
    }
  }

  def phoneLike(sub: String): List[Phone] = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.filter(_.phone like s"%$sub%").list.map { case (id, name, phone, time) => Phone(id, name, phone, time) }
    }
  }

  def idLike(id: Long): List[Phone] = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.filter(_.id === id).list.map { case (id, name, phone, time) => Phone(id, name, phone, time) }
    }
  }

  def delByTime(): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones
          .filter(_.time <= Timestamp.valueOf(LocalDateTime.now().minusYears(1)))
          .delete
    }
  }

}

object DB extends Database