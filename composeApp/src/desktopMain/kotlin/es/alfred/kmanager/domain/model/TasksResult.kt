package es.alfred.kmanager.domain.model

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class TasksResult(var result: Boolean, var tasks: List<Task>, var errorMsg: String?)
