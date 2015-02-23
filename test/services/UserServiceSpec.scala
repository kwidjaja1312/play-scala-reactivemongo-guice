package services

import java.util.concurrent.TimeUnit

import app.BaseSpec
import play.api.Logger

import scala.concurrent._
import scala.concurrent.duration._

/**
 * Unit test for [[services.UserService]]
 *
 * @author kwidjaja
 * @since 22/Feb/2015 12:28
 */
class UserServiceSpec extends BaseSpec {

  // All spec classes must invoke this method to start the fake application
  start()

  "UserService" should {

    "able to fetch list of users" in {
      val userService = new UserService
      val users = Await.result(userService.find(None), Duration(5, TimeUnit.SECONDS))
      users.size must beEqualTo(0)
    }
  }

  // All spec classes must invoke this method to stop the fake application to avoid memory leaking
  stop()
}
