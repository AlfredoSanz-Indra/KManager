package es.alfred.kmanager.core.model

import kotlinx.serialization.Serializable

/**
 * @author Alfredo Sanz
 * @time 2025
 */
@Serializable
data class Mongo(val connection: String,
                 val database: String,)
