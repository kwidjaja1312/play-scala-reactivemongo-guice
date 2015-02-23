import javax.inject.Singleton

import com.google.inject.{AbstractModule, Guice}
import play.api.mvc.{RequestHeader, Result, WithFilters}
import play.api.{Application, GlobalSettings, Logger}
import play.filters.csrf.CSRFFilter
import play.modules.reactivemongo.ReactiveMongoPlugin
import services.UserService

import scala.concurrent.Future

/**
 * Global application object as the entry point of the application
 *
 * @author kwidjaja
 * @since 2/19/15.
 */
object Global extends WithFilters(CSRFFilter()) with GlobalSettings {

  val injector = Guice.createInjector(new AbstractModule {
    override def configure(): Unit = {
      bind(classOf[UserService]).in(classOf[Singleton])
    }
  })

  override def onStart(app: Application): Unit = {
  }

  override def onStop(app: Application): Unit = {
    Logger.info("Closing mongodb driver and connection...")
    ReactiveMongoPlugin.connection(app).close()
    ReactiveMongoPlugin.driver(app).close()
  }

  override def onError(request: RequestHeader, ex: Throwable): Future[Result] = super.onError(request, ex)

  override def onHandlerNotFound(request: RequestHeader): Future[Result] = super.onHandlerNotFound(request)

  override def onBadRequest(request: RequestHeader, error: String): Future[Result] = super.onBadRequest(request, error)

  override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)
}
