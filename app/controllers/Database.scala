package controllers

import scala.slick.driver.PostgresDriver.simple._
import scala.util.{Failure, Success, Try}

case class Phone(
                  id: Long,
                  name: String,
                  phone: String)

case class PhoneForm(
                  phone: String,
                  name: String)

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

        //Try(
          phones
          .filter(_.id === id)
          .map(c => (c.name, c.phone))
          .update((pf.name, pf.phone))
      //) match {
      //    case Success(_) => println("Record successfully updated!")
      //    case Failure(_) => println("An error occurred!")
      //  }
    }
  }

  def del(id: Long): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        Try(phones
          .filter(_.id === id)
          .delete) match {
          case Success(_) => println("Record successfully updated!")
          case Failure(_) => println("An error occurred!")
        }
    }
  }

  def byName(name: String): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        phones.list foreach { row => //if row._1 like name
          println("id " + row._1 + " username " + row._2 + " phone " + row._3)
        }
    }
  }

  def clear(): Unit = {
    val connectionUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/isbgmvfg?user=isbgmvfg&password=PyjJxgZt_Gxirm6Z7hDAUOonsZiywZoM"
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val phones = TableQuery[Phonebook]

        Try(phones.delete) match {
          case Success(_) => println("Database cleared!")
          case Failure(_) => println("Database not cleared!")
        }
    }
  }

}

object DB extends Database