package es.alfred.kmanager.data.mongo.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.types.ObjectId

/**
 * @author Alfredo Sanz
 * @time 2025
 */
data class ContextProject(
    @SerialName("_id") // Use this annotation instead of @BsonId
    @Contextual
    var id: ObjectId?,
    val name: String,
    val label: String,
    val project: String)
