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
data class TasksDetailUiState(
    val taskStateList: List<String> = listOf("Pending", "Working", "Stopped", "Waiting", "Pushed", "Closed"),
    val taskStateSelectedList: MutableList<String> = mutableListOf(),
    var taskName: String = "",
    var taskJira: String = "",
    var taskDesc: String = "",
    var taskNotes: String = "",
    var taskBranches: String = "",
    var taskCommits: String = "",
    var generalError: Boolean = false,
    var generalErrorText: String = "",
    val title: String = "New Task",
    var mode: String = "new"
)

class TasksDetailViewModel: ViewModel(){
    private val logger = KotlinLogging.logger {}
    private val _uiState = MutableStateFlow(TasksDetailUiState())
    val uiState: StateFlow<TasksDetailUiState> = _uiState.asStateFlow()

    fun addTaskStateToSelectedList(taskState: String) {
        logger.info { "addTaskStateToSelectedList -> taskState: $taskState" }
        logger.info { "addTaskStateToSelectedList -> taskStateSelectedList: ${_uiState.value.taskStateSelectedList}" }
        when(taskState.startsWith("N-")) {
            true -> _uiState.value.taskStateSelectedList.remove(taskState.substring(2))
            false -> _uiState.value.taskStateSelectedList.addLast(taskState)
        }
        logger.info { "addTaskStateToSelectedList -> taskStateSelectedList: ${_uiState.value.taskStateSelectedList}" }
    }

    fun save() {
        logger.info { "save" }
    }

    fun updateMode(txt: String) {
        logger.info { "updateMode -> txt: $txt" }
        _uiState.update {
            it.copy(mode = txt)
        }
    }

    fun updateTaskName(txt: String) {
        logger.info { "updateTaskName -> txt: $txt" }
        _uiState.update {
            it.copy(taskName = txt)
        }
    }
    fun updateTaskJira(txt: String) {
        logger.info { "updateTaskJira -> txt: $txt" }
        _uiState.update {
            it.copy(taskJira = txt)
        }
    }
    fun updateTaskDesc(txt: String) {
        logger.info { "updateTaskDesc -> txt: $txt" }
        _uiState.update {
            it.copy(taskDesc = txt)
        }
    }
    fun updateTaskNotes(txt: String) {
        logger.info { "updateTaskNotes -> txt: $txt" }
        _uiState.update {
            it.copy(taskNotes = txt)
        }
    }
    fun updateTaskBranches(txt: String) {
        logger.info { "updateTaskBranches -> txt: $txt" }
        _uiState.update {
            it.copy(taskBranches = txt)
        }
    }
    fun updateTaskCommits(txt: String) {
        logger.info { "updateTaskCommits -> txt: $txt" }
        _uiState.update {
            it.copy(taskCommits = txt)
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
        updateTaskName("")
        updateMode("")
        updateTaskCommits("")
        updateTaskNotes("")
        updateTaskDesc("")
        updateTaskJira("")
        updateTaskBranches("")
        _uiState.value.taskStateSelectedList.clear()
        clearErrors()
    }

    private fun clearErrors() {
        updateGeneralError(false, "")
    }
}