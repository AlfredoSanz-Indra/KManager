package es.alfred.kmanager.view.page.tasking.viewmodel

import androidx.lifecycle.ViewModel
import es.alfred.kmanager.core.di.UseCaseFactory
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.core.util.DateTimeUtils
import es.alfred.kmanager.core.validators.ChainTextValidator
import es.alfred.kmanager.core.validators.TextValidatorLength
import es.alfred.kmanager.core.validators.ValidatorResult
import es.alfred.kmanager.domain.model.FilterTasks
import es.alfred.kmanager.domain.model.SelectData
import es.alfred.kmanager.domain.model.Task
import es.alfred.kmanager.view.context.TasksContext
import es.alfred.kmanager.view.shared.ValidationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mu.KotlinLogging

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
    var searching: Boolean = false,
    var deleting: Boolean = false,
    var lastFilterUsed: FilterTasks? = null,
    var tasksList: List<Task> = listOf(),
    var flagShowConfirmDelete: Boolean = false,
    var taskIDtoDelete: String = ""
)

class TasksListViewModel: ViewModel(){
    private val logger = KotlinLogging.logger {}
    private val _uiState = MutableStateFlow(TasksListUiState())
    val uiState: StateFlow<TasksListUiState> = _uiState.asStateFlow()

    fun init() {
        clearState()
        
        if(uiState.value.taskProjectList.isEmpty()) {
            updateTaskProjectList(TheResources.getResources().projects.map { SelectData(it.name, it.label) })
        }

        if(uiState.value.currentProject == null) {
            CoroutineScope(Dispatchers.IO).launch {
                updateCurrentProject(TasksContext.getCurrentProject())
            }
        }

        if(uiState.value.lastFilterUsed != null) {
            doSearch(uiState.value.lastFilterUsed!!)
        }
    }

    fun changeCurrentProject(project: SelectData) {
        CoroutineScope(Dispatchers.IO).launch {
            updateCurrentProject(TasksContext.changeCurrentProject(project))
        }
    }

    fun addTaskStateToSearchList(taskState: String) {
        when(taskState.startsWith("N-")) {
            true -> _uiState.value.taskStateSearchList.remove(taskState.substring(2))
            false ->   _uiState.value.taskStateSearchList.addLast(taskState)
        }
    }

    fun search() {
        updateSearching(true)

        try {
            val validateResult = validateForm()
            if(!validateResult.result) {
                updateSearching(false)
                updateGeneralError(true, "The field ${validateResult.field} ${validateResult.message}")
                return
            }
            val filter = FilterTasks(
                uiState.value.taskFieldSearch,
                uiState.value.currentProject!!.name,
                uiState.value.taskStateSearchList.toList() //make a copy of the list
            )
            doSearch(filter)
        }
        catch (e: Error) {
            updateGeneralError(true, "Searching error")
            updateSearching(false)
            logger.info { "Search -> generalError: ${_uiState.value.generalError}" }
        }
    }

    private fun doSearch(filter: FilterTasks) {
        val tasksUseCase = UseCaseFactory.getTasksUseCase()

        updateLastFilterUsed(filter)
        CoroutineScope(Dispatchers.IO).launch {
            val resp = tasksUseCase.getTasks(filter)
            updateSearching(false)
            if (resp.result) {
                updateTasksList(resp.tasks)
            } else {
                updateTasksList(listOf())
            }
        }
    }

    private fun validateForm(): ValidationResult {
        var result = ValidationResult(true, "", "")
        clearErrors()

        //----VALIDATORS----
        val chainTxtShort = ChainTextValidator(
            TextValidatorLength(5, taskShort_MAXLENGTH))

        //----VALIDATIONS----
        var valResultFieldSearch: ValidatorResult = ValidatorResult.Success
        if(uiState.value.taskFieldSearch.trim().isNotBlank()) {
            valResultFieldSearch = chainTxtShort.validate(uiState.value.taskFieldSearch.trim())
        }

        var valResultProject: ValidatorResult = ValidatorResult.Success
        if(uiState.value.currentProject == null) {
            valResultProject = ValidatorResult.Error("Project")
        }

        //----CHECK VALIDATION RESULTS----
        if(valResultProject is ValidatorResult.Error) {
            result = ValidationResult(false, "Project", "must be selected")
        }

        if(uiState.value.taskStateSearchList.size == 0) {
            result = ValidationResult(false, "States", "must have at least one chip selected")
        }

        if(valResultFieldSearch is ValidatorResult.Error) {
            result = ValidationResult(false, "Search text", valResultFieldSearch.message)
        }

        return result
    }

    fun taskDateToString(theTask: Task): String {
        val iniDate = if(theTask.dateReq != null)
                        "From ${DateTimeUtils.dateToDateString(theTask.dateReq!!)}"
                      else ""
        val endDate = if(theTask.dateEnd != null)
                          " -> ${DateTimeUtils.dateToDateString(theTask.dateEnd!!)}"
                      else ""

        return iniDate + endDate
    }

    fun deleteTask() {
        val tasksUseCase = UseCaseFactory.getTasksUseCase()

        updateDeleting(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resp = tasksUseCase.deleteTask(uiState.value.taskIDtoDelete)
                updateDeleting(false)
                updateSearching(true)
                if (resp.result && uiState.value.lastFilterUsed != null) {
                    doSearch(uiState.value.lastFilterUsed!!)
                    updateTaskIDtoDelete("")
                }
            }
            catch (err: Error) {
                updateGeneralError(true, "Error deleting Task")
                logger.info { "Error Deleting: ${err.message}" }
            }
            finally {
                updateSearching(false)
                updateDeleting(false)
            }
        }
    }

    fun showConfirmDelete(taskID: String?) {
        updateFlagShowConfirmDelete(true)
        if(!taskID.isNullOrEmpty()) {
            updateTaskIDtoDelete(taskID)
        }
        logger.info { "showConfirmDelete -> taskID: $taskID" }
    }

    fun hideConfirmDelete() {
        logger.info { "hideConfirmDelete" }
        updateFlagShowConfirmDelete(false)
    }

    private fun updateFlagShowConfirmDelete(action: Boolean) {
        _uiState.update {
            it.copy(flagShowConfirmDelete = action)
        }
    }

    private fun updateTaskIDtoDelete(id: String) {
        _uiState.update {
            it.copy(taskIDtoDelete = id)
        }
    }

    private fun updateTasksList(newList: List<Task>) {
        _uiState.update {
            it.copy(tasksList = newList)
        }
    }

    private fun updateTaskProjectList(projectList: List<SelectData>) {
        _uiState.update {
            it.copy(taskProjectList = projectList)
        }
    }

    private fun updateCurrentProject(currentProject: SelectData) {
        _uiState.update {
            it.copy(currentProject = currentProject)
        }
    }

    fun updateTaskFieldSearch(txt: String) {
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

    private fun updateSearching(value: Boolean) {
        _uiState.update {
            it.copy(searching = value)
        }
    }

    private fun updateDeleting(value: Boolean) {
        _uiState.update {
            it.copy(deleting = value)
        }
    }

    private fun updateLastFilterUsed(filter: FilterTasks?) {
        _uiState.update {
            it.copy(lastFilterUsed = filter)
        }
    }

    private fun clearState() {
        updateTaskFieldSearch("")
        updateSearching(false)
        updateDeleting(false)
        updateFlagShowConfirmDelete(false)
        updateTaskIDtoDelete("")
        //updateLastFilterUsed(null)
        _uiState.value.taskStateSearchList.clear()
        clearErrors()
    }

    private fun clearErrors() {
        updateGeneralError(false, "")
    }
}