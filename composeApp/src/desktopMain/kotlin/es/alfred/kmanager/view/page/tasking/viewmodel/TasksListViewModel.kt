package es.alfred.kmanager.view.page.tasking.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class TasksListUiState(
    val taskStateList: List<String> = listOf("Pending", "Working", "Stopped", "Waiting", "Pushed", "Closed"),
    val taskStateSearchList: MutableList<String> = mutableListOf(),
    var taskFieldSearch: String = "",
    var generalError: Boolean = false,
    var generalErrorText: String = "",
)

class TasksListViewModel: ViewModel(){
    private val logger = KotlinLogging.logger {}
    private val _uiState = MutableStateFlow(TasksListUiState())
    val uiState: StateFlow<TasksListUiState> = _uiState.asStateFlow()

    fun addTaskStateToSearchList(taskState: String) {
        logger.info { "addTaskStateToSearchList -> taskState: $taskState" }
        logger.info { "addTaskStateToSearchList -> taskStateSearchList: ${_uiState.value.taskStateSearchList}" }
        when(taskState.startsWith("N-")) {
            true -> _uiState.value.taskStateSearchList.remove(taskState.substring(2))
            false ->   _uiState.value.taskStateSearchList.addLast(taskState)
        }
        logger.info { "addTaskStateToSearchList -> taskStateSearchList: ${_uiState.value.taskStateSearchList}" }
    }

    fun search() {
        logger.info { "Search" }

        logger.info { "Search -> taskStateSearchList: ${_uiState.value.taskStateSearchList}" }
        logger.info { "Search -> taskFieldSearch: ${_uiState.value.taskFieldSearch}" }

        updateGeneralError(true, "Error de Base")
        logger.info { "Search -> generalError: ${_uiState.value.generalError}" }
    }

    fun updateTaskFieldSearch(txt: String) {
        logger.info { "updateTaskFieldSearch -> txt: ${txt}" }
        _uiState.update {
            it.copy(taskFieldSearch = txt)
        }
    }

    private fun updateGeneralError(state: Boolean, text: String) {
        _uiState.update {
            it.copy(generalError = state)
        }
        _uiState.update {
            it.copy(generalErrorText = text)
        }
    }

    private fun clearState() {
        updateTaskFieldSearch("")
        _uiState.value.taskStateSearchList.clear()
        clearErrors()
    }

    private fun clearErrors() {
        updateGeneralError(false, "")
    }
}