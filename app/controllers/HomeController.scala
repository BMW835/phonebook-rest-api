package controllers

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

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
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




  /*val phoneForm = Form(
    mapping(
      "phoneNumber" -> text(),
      "name" -> text())(PhoneForm.apply)(PhoneForm.unapply))

  def addPhone = Action { implicit request =>
    val pf: PhoneForm = phoneForm.bindFromRequest.get
    Ok(DB.add(pf))
  }*/

  /*implicit val placeReads: Reads[PhoneForm] = (
    (JsPath \ "phoneNumber").read[String] and
      (JsPath \ "name").read[String]
    )(PhoneForm.apply _)*/



  /*def addPhone = Action(parse.json) { implicit request =>
    phoneForm.bindFromRequest.fold(
      form => {
        BadRequest(views.html.index())
      },
      product => {
        val id = DB.add(product)
        Redirect(routes.ProductController.index).flashing("success"
          -> Messages("success.insert", id))
      })
  }*/

  /*def addPhone = Action(parse.json) { request =>
    val placeResult = request.body.validate[PhoneForm]
    placeResult.fold(
      form => {
        Ok(views.html.index())
        //BadRequest(views.html.index()/*Json.obj("message" -> JsError.toJson(errors))*/)
      },
      place => {
        DB.add(place)
        Redirect(routes.ImageController.index)
        //Ok(Json.obj("message" -> ("Place '" + place.name + "' saved.")))
      }
    )
  }*/



  def all() = Action {
    Ok(Json.toJson(DB.all()))
  }


}
