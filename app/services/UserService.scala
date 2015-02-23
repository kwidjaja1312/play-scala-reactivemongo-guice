package services

import models.User
import org.joda.time.DateTime
import play.api.Logger
import reactivemongo.bson._

import scala.concurrent.{ExecutionContext, Future}

/**
 * Service to provide functions related to [[models.User]] including authentication
 *
 * @author kwidjaja 
 * @since 20/Feb/2015 20:56
 */
class UserService extends BaseService {

  private def collection = getCollection("users")

  /**
   * Find list of [[models.User]]
   *
   * @param user Instance of [[models.User]] as the criteria. Optional.
   *
   * @return Array of [[BSONDocument]] instance wrapped in [[scala.concurrent.Future]]
   */
  def find(user: Option[User])(implicit ec: ExecutionContext): Future[Array[User]] = user match {
    case Some(u) => collection.find(u).cursor[User].collect[Array]()
    case None => collection.find(User()).cursor[User].collect[Array]()
  }

  /**
   * Authenticate user by username and password
   *
   * @param email Valid email address (username)
   *
   * @return Optional, user object
   */
  def authenticate(email: String, password: String)(implicit ec: ExecutionContext): Future[Option[User]] =
    if (!email.isEmpty && !password.isEmpty)
      collection.find(User(email = Some(email), secret = Some(password))).one[User]
    else
      Future {
        None
      }

  /**
   * Find user by username
   *
   * @param username Valid username (email address)
   *
   * @return Optional, user object wrapped in [[scala.concurrent.Future]]
   */
  def byUsername(username: Option[String])(implicit ec: ExecutionContext): Future[Option[User]] = username match {
    case Some(u) => collection.find(User(email = Some(u))).one[User]
    case None => Future {
      None
    }
  }
}