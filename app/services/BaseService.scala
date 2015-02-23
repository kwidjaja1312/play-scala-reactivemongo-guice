package services

import play.api.Play.current
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.DefaultDB

/**
 * Base class for services classes
 *
 * @author kwidjaja 
 * @since 21/Feb/2015 17:11
 */
abstract class BaseService {

  /**
   * Encapsulate the access to the db reference so it does not get evaluated on application start
   */
  protected def db: DefaultDB = ReactiveMongoPlugin.db

  /**
   * Get the collection object depends on the model domain type
   *
   * @param name Collection name
   */
  protected def getCollection(name: String): JSONCollection = db[JSONCollection](name)
}
