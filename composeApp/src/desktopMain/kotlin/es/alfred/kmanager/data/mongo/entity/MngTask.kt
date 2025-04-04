package es.alfred.kmanager.data.mongo.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.types.ObjectId

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class MngTask(
    @SerialName("_id") // Use this annotation instead of @BsonId
    @Contextual var _id: ObjectId?,
    val project: String,
    val states: List<String>,
    val name: String,
    var jira: String?,
    var description: String?,
    var notes: String?,
    var branches: String?,
    var commits: String?,
    var dateReq: Long?,
    var dateEnd: Long?,
    var yearReq: Int?,
    var monthReq: Int?,
    var dayReq: Int?,
    var yearEnd: Int?,
    var monthEnd: Int?,
    var dayEnd: Int?,
    val creationDate: Long
)
