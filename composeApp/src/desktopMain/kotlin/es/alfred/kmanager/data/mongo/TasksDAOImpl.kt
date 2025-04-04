package es.alfred.kmanager.data.mongo

import com.mongodb.MongoException
import es.alfred.kmanager.core.db.mongo.MongoConn
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.data.mongo.entity.MngTask
import es.alfred.kmanager.data.mongo.results.TasksResult
import es.alfred.kmanager.domain.dataapi.TasksDAO
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDAOImpl: TasksDAO {

    private val logger = KotlinLogging.logger {}

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
        logger.info { "insertTask ->  result: $result" }

        return result
    }
}