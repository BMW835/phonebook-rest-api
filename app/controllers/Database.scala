package controllers

import java.sql.Timestamp
import java.time.LocalDateTime

import models._

import scala.slick.driver.PostgresDriver.simple._

class Phonebook(tag: Tag) extends Table[(Long, String, String, Timestamp)](tag, "phones") {
  def id: Column[Long] = column[Long]("id")
  def name: Column[String] = column[String]("name")
  def phone: Column[String] = column[String]("phone")
  def time: Column[Timestamp] = column[Timestamp]("time")
  def * = (id, name, phone, time)
}

trait Database {
  def add(dbUrl: String, pf: PhoneForm): Unit = {
    Database.forURL(dbUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.map(c => (c.name, c.phone, c.time)) += (pf.name, pf.phone, Timestamp.valueOf(LocalDateTime.now()))
    }
  }

  def all(dbUrl: String): List[Phone] = {
    Database.forURL(dbUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.list.map { case (id, name, phone, time) => Phone(id, name, phone, time) }
    }
  }

  def mod(dbUrl: String, id: Long, pf: PhoneForm): Unit = {
    Database.forURL(dbUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones
          .filter(_.id === id)
          .map(c => (c.name, c.phone, c.time))
          .update((pf.name, pf.phone, Timestamp.valueOf(LocalDateTime.now())))
    }
  }

  def del(dbUrl: String, id: Long): Unit = {
    Database.forURL(dbUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones
          .filter(_.id === id)
          .delete
    }
  }

  def nameLike(dbUrl: String, sub: String): List[Phone] = {
    Database.forURL(dbUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.filter(_.name like s"%$sub%").list.map { case (id, name, phone, time) => Phone(id, name, phone, time) }
    }
  }

  def phoneLike(dbUrl: String, sub: String): List[Phone] = {
    Database.forURL(dbUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.filter(_.phone like s"%$sub%").list.map { case (id, name, phone, time) => Phone(id, name, phone, time) }
    }
  }

  def delByTime(dbUrl: String): Unit = {
    Database.forURL(dbUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones
          .filter(_.time <= Timestamp.valueOf(LocalDateTime.now().minusYears(1)))
          .delete
    }
  }

}

object DB extends Database