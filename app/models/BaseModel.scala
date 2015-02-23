package models

import org.joda.time.DateTime
import play.api.libs.json._

/**
 * Base model to share the reference to implicit vals for JSON de/serialize
 *
 * @author kwidjaja
 * @since 22/Feb/2015 15:19
 */
abstract class BaseModel {

  /**
   * I get this way to read org.joda.datetime from the reactivemongo user groups in google
   */	
  implicit val dateTimeReads = new Reads[DateTime] {
    def reads(jv: JsValue) = {
      jv match {
        case JsObject(Seq(("$date", JsNumber(millis)))) => JsSuccess(new DateTime(millis.toLong))
        case _ => throw new Exception(s"Unknown JsValue for DateTime: $jv")
      }
    }
  }

  import reactivemongo.bson.BSONDateTime
  import play.modules.reactivemongo.json.BSONFormats._

  /**
   * I get this way to read org.joda.datetime from the reactivemongo user groups in google
   */
  implicit val dateTimeWrites = new Writes[DateTime] {
    def writes(dt: DateTime): JsValue = {
      Json.toJson(BSONDateTime(dt.getMillis)) // {"$date": millis}
    }
  }

  val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"

  /**
   * This is to override the implicit Reads/Writes of org.joda.datetime by Play
   */
  protected val externalDateTimeReads = Reads.jodaDateReads(dateTimePattern)
  protected val externalDateTimeWrites = Writes.jodaDateWrites(dateTimePattern)
}
