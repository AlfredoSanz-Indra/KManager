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

    override suspend fun addBranch(project: String, branchList: List<String>): InsertResult {
        logger.info { "addBranch -> project: $project , branchList: $branchList " }
        var result = InsertResult("", true)

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<Document>("branches")

            val document = Document("_id", ObjectId())
                .append("project", project)
                .append("branches", branchList)

            val res = collection.insertOne(document)
            result.id = res.insertedId?.asObjectId()?.value.toString()
        }
        catch (me: MongoException) {
            logger.error { "Error inserting branches -> $me" }
            result = InsertResult("", false)
        }
        logger.info { "addBranch ->  result: $result" }
        return result
    }

    override suspend fun updateBranches(project: String, branchList: List<String>): InsertResult {
        logger.info { "updateBranches -> project: $project , branchList: $branchList " }
        var result = InsertResult("", true)

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<Document>("branches")

            val updateParams = Updates.set("branches", branchList)
            val queryParam = Filters.eq("project", project)

            collection.updateOne(filter = queryParam, update = updateParams).also {
                result.id = it.upsertedId?.asObjectId()?.value.toString()
            }
        }
        catch (me: MongoException) {
            logger.error { "Error updating branches -> $me" }
            result = InsertResult("", false)
        }
        logger.info { "updateBranches ->  result: $result" }
        return result
    }

    override suspend fun getBranches(project: String): BranchesResult {
        logger.info { "getBranches -> project: $project" }
        val result = BranchesResult(mutableListOf(), true)

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<Branches>(collectionName = "branches")

            collection.find<Branches>().limit(1).collect {
                if(!it.branches.isNullOrEmpty()) {
                    result.branches = it.branches.map { it }
                }
            }
        }
        catch (me: MongoException) {
            logger.error { "Error requesting branches from Mongo -> $me" }
            result.result = false
        }
        logger.info { "getBranches ->  result: $result" }
        return result
    }
}