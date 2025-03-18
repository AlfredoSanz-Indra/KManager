package es.alfred.kmanager.view.page.tasking.viewmodel

import androidx.lifecycle.ViewModel
import es.alfred.kmanager.core.util.DateTimeUtils
import es.alfred.kmanager.view.page.tasking.sections.TasksStateModeEnum
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
    var taskDateReq: Long = 0,
    var taskDateReqFormatted: String = "",
    var taskDateEnd: Long = 0,
    var taskDateEndFormatted: String = "",
    var generalError: Boolean = false,
    var generalErrorText: String = "",
    val title: String = "New Task",
    var mode: Int = 1,
    var showTaskDateReqDialog: Boolean = false,
    var showTaskDateEndDialog: Boolean = false,
)

class TasksDetailViewModel: ViewModel(){
    private val logger = KotlinLogging.logger {}
    private val _uiState = MutableStateFlow(TasksDetailUiState())
    val uiState: StateFlow<TasksDetailUiState> = _uiState.asStateFlow()

    fun setStateMode(stateMode: TasksStateModeEnum) {
        logger.info { "setStateMode ->  stateMode: $stateMode" }
        this.updateMode(stateMode.stateMode)

        when(stateMode) {
            TasksStateModeEnum.NEW_TASK -> creatingStateModeInit()
            else -> updatingStateModeInit()
        }
    }

    private fun creatingStateModeInit() {
        logger.info { "creatingStateModeInit" }
        updateTitle("New Task")
        updateTaskDateReq(DateTimeUtils.currentDate(), DateTimeUtils.currentDateFormatted())
        updateTaskDateEnd(DateTimeUtils.currentDate(), DateTimeUtils.currentDateFormatted())
    }

    private fun updatingStateModeInit() {
        logger.info { "updatingStateModeInit" }
        updateTitle("Update Task")
    }

    fun addTaskStateToSelectedList(taskState: String) {
        logger.info { "addTaskStateToSelectedList -> taskState: $taskState" }
        logger.info { "addTaskStateToSelectedList -> taskStateSelectedList: ${_uiState.value.taskStateSelectedList}" }
        when(taskState.startsWith("N-")) {
            true -> _uiState.value.taskStateSelectedList.remove(taskState.substring(2))
            false -> _uiState.value.taskStateSelectedList.addLast(taskState)
        }
        logger.info { "addTaskStateToSelectedList -> taskStateSelectedList: ${_uiState.value.taskStateSelectedList}" }
    }

    fun onDateReqSelected(dateInMill: Long) {
        val dateFormatted = DateTimeUtils.dateToDateString(dateInMill)
        logger.info { "onDateReqSelected -> dateFormatted: $dateFormatted" }
        updateTaskDateReq(dateInMill, dateFormatted)
        updateShowTaskDateReqDialog(false)
    }

    fun onDateEndSelected(dateInMill: Long) {
        val dateFormatted = DateTimeUtils.dateToDateString(dateInMill)
        logger.info { "onDateEndSelected -> dateFormatted: $dateFormatted" }
        updateTaskDateEnd(dateInMill, dateFormatted)
        updateShowTaskDateEndDialog(false)
    }


    fun save() {
        logger.info { "save" }
    }

    private fun updateMode(num: Int) {
        logger.info { "updateMode -> num: $num" }
        _uiState.update {
            it.copy(mode = num)
        }
    }

    private fun updateTitle(txt: String) {
        logger.info { "updateTitle -> txt: $txt" }
        _uiState.update {
            it.copy(title = txt)
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
    private fun updateTaskDateReq(dateInMill: Long, dateInStr: String) {
        _uiState.update {
            it.copy(taskDateReq = dateInMill)
        }

        _uiState.update {
            it.copy(taskDateReqFormatted = dateInStr)
        }
    }
    private fun updateTaskDateEnd(dateInMill: Long, dateInStr: String) {
        _uiState.update {
            it.copy(taskDateEnd = dateInMill)
        }

        _uiState.update {
            it.copy(taskDateEndFormatted = dateInStr)
        }
    }
    fun updateShowTaskDateReqDialog(action: Boolean) {
        logger.info { "updateShowTaskDateReqDialog -> action: $action" }
        _uiState.update {
            it.copy(showTaskDateReqDialog = action)
        }
    }
    fun updateShowTaskDateEndDialog(action: Boolean) {
        logger.info { "updateShowTaskDateEndDialog -> action: $action" }
        _uiState.update {
            it.copy(showTaskDateEndDialog = action)
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
        updateTitle("")
        updateTaskName("")
        updateTaskCommits("")
        updateTaskNotes("")
        updateTaskDesc("")
        updateTaskDateReq(0, "")
        updateTaskDateEnd(0, "")
        updateTaskJira("")
        updateTaskBranches("")
        _uiState.value.taskStateSelectedList.clear()
        clearErrors()
    }

    private fun clearErrors() {
        updateGeneralError(false, "")
    }
}