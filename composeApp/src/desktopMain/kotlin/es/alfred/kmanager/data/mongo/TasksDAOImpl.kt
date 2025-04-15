package es.alfred.kmanager.data.mongo

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import es.alfred.kmanager.core.db.mongo.MongoConn
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.data.mongo.entity.MngTask
import es.alfred.kmanager.data.mongo.model.MgFilterTask
import es.alfred.kmanager.data.mongo.results.SimpleResult
import es.alfred.kmanager.data.mongo.results.TasksListResult
import es.alfred.kmanager.data.mongo.results.TasksResult
import es.alfred.kmanager.domain.dataapi.TasksDAO
import mu.KotlinLogging
import org.bson.Document
import org.bson.types.ObjectId

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDAOImpl: TasksDAO {

    private val logger = KotlinLogging.logger {}

    override suspend fun deleteTask(taskID: String): SimpleResult {
        logger.info { "deleteTask -> taskID: $taskID" }
        var result = SimpleResult(false)

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<MngTask>("tasks")

            val filter = Filters.eq("_id", ObjectId(taskID))

            collection.deleteOne(filter).also {
                if(it.deletedCount > 0) {
                    result = SimpleResult(true)
                }
            }
        }
        catch (me: MongoException) {
            logger.error { "deleteTask -> Error deleting task -> $me" }
            result = SimpleResult(false)
        }
        logger.info { "deleteTask ->  ${result.result}" }
        return result
    }

    override suspend fun insertTask(task: MngTask): TasksResult {
        logger.info { "insertTask -> task: $task" }
        var result = TasksResult(false, null)

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<MngTask>("tasks")

            collection.insertOne(task).also {
                if(it.insertedId != null) {
                    task._id = it.insertedId?.asObjectId()?.value
                    result = TasksResult(true, task)
                }
            }
        }
        catch (me: MongoException) {
            logger.error { "insertTask -> Error inserting contextProject current -> $me" }
            result = TasksResult(false, null)
        }
        logger.info { "insertTask ->  ${result.result}" }
        return result
    }

    override suspend fun getTask(id: String): TasksResult {
        logger.info { "getTask -> id: $id" }
        var result = TasksResult(false, null)

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<Document>("tasks")

            val filter = Filters.eq("_id", ObjectId(id))

            collection.find<MngTask>(filter = filter).limit(1)
                .collect {
                    result.data = it
                }
            result.result = true
        }
        catch (me: MongoException) {
            logger.error { "getTask -> Error getting tasks -> $me" }
            result = TasksResult(false, null)
        }
        logger.info { "getTask -> result: ${result.result}" }
        return result
    }

    override suspend fun getTasks(filter: MgFilterTask): TasksListResult {
        logger.info { "getTasks -> filter: $filter" }
        var result = TasksListResult(true, listOf())

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<Document>("tasks")

            val queryParams = Filters.and(Filters.eq("project", filter.project),
                                          Filters.`in`("states", filter.states),
                                          if(!filter.name.isNullOrEmpty()) {
                                              Filters.text(filter.name!!)
                                          }
                                          else {
                                              Filters.empty()
                                          }
                                          )
            val sorts = Sorts.descending("creationDate")

            val resultList: MutableList<MngTask> = mutableListOf()
            collection.find<MngTask>(filter = queryParams)
                      .sort(sorts)
                      .collect {

                          resultList.addLast(it)
                      }
            result.data = resultList.toList()
        }
        catch (me: MongoException) {
            logger.error { "getTasks -> Error getting tasks -> $me" }
            result = TasksListResult(false, listOf())
        }
        logger.info { "getTasks -> result: ${result.result}" }
        return result
    }
}