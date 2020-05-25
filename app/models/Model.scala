package models

case class Phone(
                  id: Long,
                  name: String,
                  phone: String)

case class PhoneForm(
                      phone: String,
                      name: String)