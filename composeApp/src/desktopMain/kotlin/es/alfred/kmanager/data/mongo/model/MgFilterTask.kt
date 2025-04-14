package es.alfred.kmanager.data.mongo.model

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class MgFilterTask(
    val project: String,
    var states: List<String>,
    var name: String?,
    var description: String?,
    var notes: String?,
)