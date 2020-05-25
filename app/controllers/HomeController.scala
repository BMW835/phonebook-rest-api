package controllers

import models._
import javax.inject._
import play.api.data._
import play.api.mvc._
import play.api.libs.json._

import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms.text
import play.api.mvc.{ AbstractController, ControllerComponents }

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def getName = Action {
    Ok(Json.toJson("todo"))
  }

  val phoneForm: Form[PhoneForm] = Form(
    mapping(
      "phoneNumber" -> text,
      "name" -> text
    )(PhoneForm.apply)(PhoneForm.unapply))

  def addPhone()= Action { implicit request =>
    phoneForm.bindFromRequest.fold(
      _ => { BadRequest(views.html.index()) },
      phone => {
        DB.add(phone)
        Redirect(routes.HomeController.index)
      })
  }

  implicit val todoFormat = Json.format[Phone]

  def getPhones() = Action {
    Ok(
      Json.toJson(
        DB.all().map { p =>
          Map("phoneNumber" -> p.phone, "name" -> p.name)
        }
      )
    )
  }

  def modPhone(id: Long)= Action { implicit request =>
    phoneForm.bindFromRequest.fold(
      _ => { BadRequest(views.html.index()) },
      phone => {
        DB.mod(id, phone)
        Redirect(routes.HomeController.index)
      })
  }

  def delPhone(id: Long) = Action { //implicit request =>
    DB.del(id)
    //Ok
    Redirect(routes.HomeController.index)
  }

  def byName(nameSubstring: String) = Action { //implicit request =>
    Ok(
      Json.toJson(
        DB.nameLike(nameSubstring).map { p =>
          Map("phoneNumber" -> p.phone, "name" -> p.name)
        }
      )
    )
  }

  def byNumber(phoneSubstring: String) = Action { //implicit request =>
    Ok(
      Json.toJson(
        DB.phoneLike(phoneSubstring).map { p =>
          Map("phoneNumber" -> p.phone, "name" -> p.name)
        }
      )
    )
  }
  

  def all() = Action {
    Ok(Json.toJson(DB.all()))
  }


}
