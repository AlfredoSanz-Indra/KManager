package es.alfred.kmanager.domain.model

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class FilterTasks(
    var searchField: String,
    var project: String,
    var states: List<String>)
