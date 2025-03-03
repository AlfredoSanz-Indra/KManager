package es.alfred.kmanager.data.mongo

import com.mongodb.MongoException
import es.alfred.kmanager.core.db.mongo.MongoConn
import es.alfred.kmanager.data.mongo.entity.ServerAlive
import es.alfred.kmanager.domain.dataapi.MongoDAO
import mu.KotlinLogging
import org.bson.BsonInt64
import org.bson.Document

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class MongoDAOImpl : MongoDAO {
    private val logger = KotlinLogging.logger {}

    override suspend fun checkServerIsAlive(): ServerAlive {
        var result: ServerAlive

        val mongoClient = MongoConn.getClient()
        val database = mongoClient.getDatabase("kmanagerDB")
        try {
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