package models

import java.sql.Timestamp

case class Phone(
                  id: Long,
                  name: String,
                  phone: String,
                  time: Timestamp)

case class PhoneForm(
                      phone: String,
                      name: String)
