package controllers

import javax.inject.Singleton

import controllers.action.AuthenticatedAction
import play.api._
import play.api.mvc._
import play.filters.csrf.CSRFAddToken

/**
 * Main controller to serve requests at the entry point of application such as sign in, etc.
 *
 * @author kwidjaja
 * @since 18/02/2015
 */
@Singleton
class Application extends Controller {

  /**
   * Action to serves redirection to home page
   */
  def index = Action {
    Logger.info("Landing on index action...")
    Redirect(routes.Application.login())
  }

  /**
   * Action to serves login page
   */
  def login(returnTo: Option[String]) = CSRFAddToken {
    Action { implicit request =>
      Logger.info("Serving login page... with errors: " + request.flash.get("errors"))
      import models.User

      request.flash.get("errors") match {
        case Some(e) => Ok(views.html.signin(returnTo, User.loginForm.withError("errors", e)))
        case None => Ok(views.html.signin(returnTo, User.loginForm))
      }
    }
  }

  /**
   * Action to serves home page
   */
  def home = AuthenticatedAction { implicit request =>
    Logger.info("Serving home page...")

    request.username match {
      case Some(name) => Ok(views.html.home())
      case None => Redirect(routes.Application.login(None).url)
    }
  }
}
