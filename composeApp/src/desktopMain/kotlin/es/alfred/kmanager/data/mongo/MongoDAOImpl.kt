package es.alfred.kmanager.data.mongo

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import es.alfred.kmanager.core.db.mongo.MongoConn
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.data.mongo.entity.Branches
import es.alfred.kmanager.data.mongo.entity.BranchesResult
import es.alfred.kmanager.data.mongo.entity.ServerAlive
import es.alfred.kmanager.domain.dataapi.MongoDAO
import es.alfred.kmanager.domain.model.InsertResult
import mu.KotlinLogging
import org.bson.BsonInt64
import org.bson.Document
import org.bson.types.ObjectId

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class MongoDAOImpl : MongoDAO {
    private val logger = KotlinLogging.logger {}

    override suspend fun checkServerIsAlive(): ServerAlive {
        var result: ServerAlive

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)

            val command = Document("ping", BsonInt64(1))
            database.runCommand(command)
            result = ServerAlive(true)
        }
        catch (me: MongoException) {
            logger.error { "Error trying to connect Mongo -> $me" }
            result = ServerAlive(false)
        }

        return result
    }


}