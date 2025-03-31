package es.alfred.kmanager.data.mongo.results

/**
 * @author Alfredo Sanz
 * @time 2025
 */
data class TasksContextResult(var id: String, var result: Boolean, var data: MutableMap<String, Any>)
