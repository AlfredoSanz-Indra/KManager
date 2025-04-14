package es.alfred.kmanager.data.mongo

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import es.alfred.kmanager.core.db.mongo.MongoConn
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.data.mongo.entity.MngContext
import es.alfred.kmanager.data.mongo.entity.MngContextProject
import es.alfred.kmanager.data.mongo.results.TasksContextResult
import es.alfred.kmanager.domain.dataapi.TasksContextDAO
import mu.KotlinLogging
import org.bson.Document

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksContextDAOImpl : TasksContextDAO {

    private val logger = KotlinLogging.logger {}


    override suspend fun upsertTasksContextProject(context: MngContext): TasksContextResult {
        logger.info { "upsertTasksContextProject -> contextProject: $context" }
        var result = TasksContextResult("", false, mutableMapOf())

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<Document>("tasksContext")

            val updateParams = Updates.set("project", context.project)
            val queryParam = Filters.empty()
            val options = UpdateOptions().upsert(true)

            collection.updateOne(filter = queryParam, update = updateParams, options = options).also {
                context.id = it.upsertedId?.asObjectId()?.value
                result.id = context.id.toString()
                result.data["context"] = context
                result.result = true
            }
        }
        catch (me: MongoException) {
            logger.error { "upsertTasksContextProject -> Error upserting contextProject current -> $me" }
            result = TasksContextResult("", false, mutableMapOf())
        }
        logger.info { "upsertTasksContextProject ->  ${result.result}" }
        return result
    }

    override suspend fun getTasksContextCurrentProject(): TasksContextResult {
        logger.info { "getTasksContextCurrentProject " }
        var result = TasksContextResult("", false, mutableMapOf())

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<Document>("tasksContext")

            val queryParam = Filters.eq("project.time", "current")

            collection.find<MngContext>(filter = queryParam).limit(1).collect {
                if(it.project != null) {
                    val project = MngContextProject( it.project!!.name, it.project!!.label, it.project!!.time)
                    val context = MngContext(it.id, project)
                    result.id = it.id.toString()
                    result.data["context"] = context
                    result.result = true
                }
            }
        }
        catch (me: MongoException) {
            logger.error { "getTasksContextCurrentProject -> Error upserting tasksContext -> $me" }
            result = TasksContextResult("", false, mutableMapOf())
        }

        logger.info { "getTasksContextCurrentProject -> result: ${result.result}" }
        return result
    }
}