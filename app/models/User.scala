package models

import org.joda.time.DateTime
import reactivemongo.bson._

/**
 * Model to hold User data
 *
 * @author kwidjaja 
 * @since 2/19/15.
 */
case class User(id: Option[BSONObjectID] = None,
                email: Option[String] = None,
                secret: Option[String] = None,
                registrationDate: Option[DateTime] = None,
                lastLogin: Option[DateTime] = None,
                lastUpdated: Option[DateTime] = None)

object User extends BaseModel {

  import play.api.data.Form
  import play.api.data.Forms._

  /* Login form */
  lazy val loginForm: Form[(String, String)] = Form(
    tuple(
      "username" -> email,
      "password" -> nonEmptyText(minLength = 8)
    ))

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads._
  import play.api.libs.json._
  import play.modules.reactivemongo.json.BSONFormats._

  implicit val userReads: Reads[User] =
    (__ \ "id").readNullable[BSONObjectID].and(
      (__ \ "email").readNullable[String]).and(
        (__ \ "secret").readNullable[String]).and(
        (__ \ "registrationDate").readNullable[DateTime](externalDateTimeReads)).and(
        (__ \ "lastLogin").readNullable[DateTime](externalDateTimeReads)).and(
        (__ \ "lastUpdated").readNullable[DateTime](externalDateTimeReads))(User.apply _)

  implicit val userWrites: Writes[User] =
    (__ \ "id").writeNullable[BSONObjectID].and(
      (__ \ "email").writeNullable[String]).and(
        (__ \ "secret").writeNullable[String]).and(
        (__ \ "registrationDate").writeNullable[DateTime](externalDateTimeWrites)).and(
        (__ \ "lastLogin").writeNullable[DateTime](externalDateTimeWrites)).and(
        (__ \ "lastUpdated").writeNullable[DateTime](externalDateTimeWrites))(unlift(User.unapply))
}
