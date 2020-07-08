package controllers

import akka.actor.ActorSystem
import controllers.CustomMappers._
import models._
import javax.inject._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.mvc.ControllerComponents
import play.api.libs.json.Json
import play.api.routing.JavaScriptReverseRouter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import java.io._
import scala.io.Source

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  implicit val todoFormat = Json.format[Phone]

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok( views.html.index("Phonebook") )
  }

  val phoneForm: Form[PhoneForm] = Form(
    mapping(
      "phoneNumber" -> text,
      "name" -> text
    )(PhoneForm.apply)(PhoneForm.unapply))

  def addPhone()= Action { implicit request =>
    phoneForm.bindFromRequest.fold(
      _ => { BadRequest(views.html.index("Phonebook")) },
      contact => {
        DB.add(contact)
        Redirect(routes.HomeController.index)
      })
  }

  def getPhones() = Action {
    Ok(Json.toJson(DB.all()))
  }

  def modPhone(id: Long)= Action { implicit request =>
    phoneForm.bindFromRequest.fold(
      _ => { BadRequest(views.html.index("Phonebook")) },
      contact => {
        DB.mod(id, contact)
        Redirect(routes.HomeController.index)
      })
  }

  def delPhone(id: Long) = Action {
    DB.del(id)
    Ok
  }

  def byName(nameSubstring: String) = Action {
    Ok(
      Json.toJson(
        DB.nameLike(nameSubstring)
      )
    )
  }

  def byPhone(phoneSubstring: String) = Action {
    Ok(
      Json.toJson(
        DB.phoneLike(phoneSubstring)
      )
    )
  }

  def byId(idSubstring: Long) = Action {
    Ok(
      Json.toJson(
        DB.idLike(idSubstring)
      )
    )
  }

  def printPhone()= Action { implicit request =>
      phoneForm.bindFromRequest.fold(
        _ => { BadRequest(views.html.index("Phonebook")) },
        contact => {
          val n = contact.name
          val p = contact.phone
          new PrintWriter(s"/tmp/$n.txt") { write(s"$n,$p"); close }
          DB.add(contact)
          Redirect(routes.HomeController.index)
        }
      )
  }

  def readPhone(inputPath: String) = Action {
    Ok {
      val file = Source.fromFile(inputPath).getLines.mkString.split(",").toList
      val contact = PhoneForm(file(1), file(0))
      DB.add(contact)
      views.html.index("Phonebook")
    }
  }

  def delByTime() = Action {
    DB.delByTime()
    Ok
  }

  def jsRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.HomeController.addPhone,
        routes.javascript.HomeController.getPhones,
        routes.javascript.HomeController.modPhone,
        routes.javascript.HomeController.delPhone,
        routes.javascript.HomeController.byName,
        routes.javascript.HomeController.byPhone,
        routes.javascript.HomeController.byId,
        routes.javascript.HomeController.printPhone,
        routes.javascript.HomeController.readPhone
      )).as("text/javascript")
  }

  val system: ActorSystem = ActorSystem()
  system.scheduler.scheduleAtFixedRate(initialDelay = 1.seconds, interval = 1.days) {
    new Runnable() {
      override def run(): Unit = {
        DB.delByTime()
      }
    }
  }

}
