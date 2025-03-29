package es.alfred.kmanager.view.page.tasking.viewmodel

import androidx.lifecycle.ViewModel
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.view.shared.SelectData
import es.alfred.kmanager.view.state.TasksContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mu.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class TasksListUiState(
    val taskStateList: List<String> = listOf("Pending", "Working", "Stopped", "Waiting", "Pushed", "Closed"),
    val taskStateSearchList: MutableList<String> = mutableListOf(),
    val taskProjectList: List<SelectData> = mutableListOf(),
    var taskFieldSearch: String = "",
    var currentProject: SelectData? = null,
    var generalError: Boolean = false,
    var generalErrorText: String = "",
)

class TasksListViewModel: ViewModel(){
    private val logger = KotlinLogging.logger {}
    private val _uiState = MutableStateFlow(TasksListUiState())
    val uiState: StateFlow<TasksListUiState> = _uiState.asStateFlow()

    fun init() {
        logger.info { "init A" }

        if(uiState.value.taskProjectList.isEmpty()) {
            updateTaskProjectList(TheResources.getResources().projects.map { SelectData(it.name, it.label) })
        }

        if(uiState.value.currentProject == null) {
            logger.info { "init b" }
            CoroutineScope(Dispatchers.IO).launch {
                updateCurrentProject(TasksContext.getCurrentProject())
            }
        }
    }

    fun changeCurrentProject(project: SelectData) {
        logger.info { "changeCurrentProject -> project: $project" }

        logger.info { "init b" }
        CoroutineScope(Dispatchers.IO).launch {
            updateCurrentProject(TasksContext.changeCurrentProject(project))
        }
    }

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

    fun navNewTask() {
        logger.info { "navNewTask" }
    }


    private fun updateTaskProjectList(projectList: List<SelectData>) {
        logger.info { "updateTaskProjectList -> projectList: ${projectList}" }
        _uiState.update {
            it.copy(taskProjectList = projectList)
        }
    }

    private fun updateCurrentProject(currentProject: SelectData) {
        logger.info { "updateCurrentProject -> currentProject: ${currentProject}" }
        _uiState.update {
            it.copy(currentProject = currentProject)
        }
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