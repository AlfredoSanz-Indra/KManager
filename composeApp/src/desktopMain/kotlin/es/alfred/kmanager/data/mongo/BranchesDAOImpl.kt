package es.alfred.kmanager.data.mongo

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import es.alfred.kmanager.core.db.mongo.MongoConn
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.data.mongo.entity.MngBranches
import es.alfred.kmanager.data.mongo.results.BranchesResult
import es.alfred.kmanager.data.mongo.results.InsertResult
import es.alfred.kmanager.domain.dataapi.BranchesDAO
import mu.KotlinLogging
import org.bson.Document
import org.bson.types.ObjectId

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class BranchesDAOImpl : BranchesDAO {

    private val logger = KotlinLogging.logger {}

    override suspend fun addBranch(project: String, branchList: List<String>): InsertResult {
        logger.info { "addBranch -> project: $project , branchList: $branchList " }
        var result = InsertResult("", true, null)

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
        catch (me: Exception) {
            logger.error { "Error inserting branches -> $me" }
            result = InsertResult("", false, me.message)
        }
        logger.info { "addBranch ->  result: ${result.result}" }
        return result
    }

    override suspend fun updateBranches(project: String, branchList: List<String>): InsertResult {
        logger.info { "updateBranches -> project: $project , branchList: $branchList " }
        var result = InsertResult("", true, null)

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
        catch (me: Exception) {
            logger.error { "Error updating branches -> $me" }
            result = InsertResult("", false, me.message)
        }
        logger.info { "updateBranches ->  result: ${result.result}" }
        return result
    }

    override suspend fun getBranches(project: String): BranchesResult {
        logger.info { "getBranches -> project: $project" }
        var result = BranchesResult(mutableListOf(), true, null)

        try {
            val mongoClient = MongoConn.getClient()
            val database = mongoClient.getDatabase(TheResources.getResources().mongo.database)
            val collection = database.getCollection<MngBranches>(collectionName = "branches")

            val filter = Filters.eq("project", project)

            collection.find<MngBranches>(filter).limit(1).collect {
                if(!it.branches.isNullOrEmpty()) {
                    result.branches = it.branches.map { it }
                }
            }
        }
        catch (me: Exception) {
            logger.error { "Error requesting branches from Mongo -> $me" }
            result = BranchesResult( listOf(), false, me.message)
        }
        logger.info { "getBranches ->  result: ${result.result}" }
        return result
    }
}