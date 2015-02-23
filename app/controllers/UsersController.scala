package controllers

import javax.inject.{Inject, Singleton}

import models.User
import play.api._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.filters.csrf.CSRFCheck
import services.UserService

import scala.concurrent.Await
import scala.language.postfixOps

/**
 * Controller to serve requests related to user model.
 *
 * @author kwidjaja
 * @since 18/02/2015
 */
@Singleton
class UsersController @Inject()(userService: UserService) extends Controller {

  /**
   * Action to authenticate sign in request
   */
  def authenticate = CSRFCheck {
    Action(parse.urlFormEncoded) { implicit request =>
      Logger.info("Authenticating user...")
      User.loginForm.bindFromRequest.fold(
        formErrors => {
          Redirect(routes.Application.login()).flashing("errors" -> "Invalid username or password")
        },

        forms => {
          val authResult = userService.authenticate(forms._1, forms._2) map {
            case user => Redirect(routes.Application.home()).addingToSession("username" -> forms._1)
          } recover {
            case e: Exception =>
              Logger.error("FAILED Authentication", e)
              Redirect(routes.Application.login()).flashing("errors" -> "User not found with given username and password")
          }

          import scala.concurrent.duration._
          Await.result(authResult, 5 seconds)
        }
      )
    }
  }

  /**
   * Action to serve forgot password page
   */
  def logout = Action { implicit request =>
    request.session - "username"
    Redirect(routes.Application.login(None).url)
  }
}
