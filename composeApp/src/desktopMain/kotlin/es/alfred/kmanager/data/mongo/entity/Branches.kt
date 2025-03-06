package es.alfred.kmanager.data.mongo.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId


/**
 * @author Alfredo Sanz
 * @time 2025
 */
@Serializable
data class Branches(
    @SerialName("_id") // Use this annotation instead of @BsonId
    @Contextual val id: ObjectId?,
    val project: String,
    val branches: List<String>?
)