package es.alfred.kmanager.data.mongo.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.types.ObjectId

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class MngTaskID(
    @SerialName("_id") // Use this annotation instead of @BsonId
    @Contextual var _id: ObjectId?,
)