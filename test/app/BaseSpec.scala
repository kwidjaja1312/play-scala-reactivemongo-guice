package app

import org.specs2.mutable.Specification
import play.api.test.FakeApplication

/**
 * Base class for all spec classes
 *
 * @author kwidjaja 
 * @since 23/Feb/2015 09:44
 */
class BaseSpec extends Specification {

  val additionalConfiguration: Map[String, String] = Map("mongodb.db" -> "test-db")

  protected def application = FakeApplication(additionalConfiguration = additionalConfiguration)

  /**
   * Start the application (fake one)
   */
  def start(): Unit = step(play.api.Play.start(application))

  /**
   * Stop the application (fake one) 
   */
  def stop(): Unit = step(play.api.Play.stop())
}
