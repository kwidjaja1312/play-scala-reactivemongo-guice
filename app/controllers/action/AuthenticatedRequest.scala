package controllers.action

import play.api.mvc._

import scala.concurrent.Future

/**
 * Custom request to wrapped username in the request to authenticate incoming request
 *
 * @author kwidjaja 
 * @since 2/20/15.
 */
class AuthenticatedRequest[A](val username: Option[String], httpRequest: Request[A]) extends WrappedRequest[A](httpRequest)

object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] with ActionTransformer[Request, AuthenticatedRequest] {

  override protected def transform[A](request: Request[A]): Future[AuthenticatedRequest[A]] = Future.successful {
    new AuthenticatedRequest[A](request.session.get("username"), request)
  }
}
