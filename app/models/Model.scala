package models

case class Phone(
                  id: Long,
                  name: String,
                  phone: String)

case class PhoneForm(
                      phone: String,
                      name: String)

object NameStorage {
  private var mName = "Bob1"

  def setName(name: String): Unit = {
    mName = name
  }

  def getName: String = mName
}