package es.alfred.kmanager.view.page.tasking.viewmodel

import androidx.lifecycle.ViewModel
import es.alfred.kmanager.core.util.DateTimeUtils
import es.alfred.kmanager.core.validators.ChainTextValidator
import es.alfred.kmanager.core.validators.DateGreaterValidator
import es.alfred.kmanager.core.validators.TextValidatorLength
import es.alfred.kmanager.core.validators.ValidatorResult
import es.alfred.kmanager.view.page.tasking.sections.TasksStateModeEnum
import es.alfred.kmanager.view.shared.ValidationResult
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
    val title: String = "No Task",
    var mode: Int = 1,
    var showTaskDateReqDialog: Boolean = false,
    var showTaskDateEndDialog: Boolean = false,
)

val taskShort_MAXLENGTH = 50
val taskLong_MAXLENGTH = 1000


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
        updateTitle("New Task")
        updateTaskDateReq(DateTimeUtils.currentDate(), DateTimeUtils.currentDateFormatted())
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
        val dateFormatted = calcDateFormatted(dateInMill)
        updateTaskDateReq(dateInMill, dateFormatted)
        updateShowTaskDateReqDialog(false)
    }

    fun onDateEndSelected(dateInMill: Long) {
        val dateFormatted = calcDateFormatted(dateInMill)
        updateTaskDateEnd(dateInMill, dateFormatted)
        updateShowTaskDateEndDialog(false)
    }

    private fun calcDateFormatted(dateInMill: Long): String {
        return if (dateInMill != 0L) {
            DateTimeUtils.dateToDateString(dateInMill)
        }
        else {
            ""
        }
    }

    fun getCurrentDate(): Long {
        return DateTimeUtils.currentDate()
    }

    fun save() {
        logger.info { "save" }

        val validateResult = validateForm()
        if(!validateResult.result) {
            logger.info { "save -> Error validating: ${validateResult.message}" }
            updateGeneralError(true, validateResult.message)
            return
        }
        logger.info { "save ->  validation success" }


    }


    private fun validateForm(): ValidationResult {
        var result = ValidationResult(true, "", "")
        clearErrors()

        val chainTxtShort = ChainTextValidator(
            TextValidatorLength(5, taskShort_MAXLENGTH)
        )
        val chainTxtLong = ChainTextValidator(
            TextValidatorLength(5, taskLong_MAXLENGTH)
        )
        val dateValidator = DateGreaterValidator()

        val valResultName = chainTxtShort.validate(uiState.value.taskName.trim())

        var valResultJira: ValidatorResult = ValidatorResult.Success
        if(!uiState.value.taskJira.isNullOrBlank()) {
            valResultJira = chainTxtShort.validate(uiState.value.taskJira.trim())
        }

        var valResultDesc: ValidatorResult = ValidatorResult.Success
        if(!uiState.value.taskDesc.trim().isNullOrBlank()) {
            valResultDesc = chainTxtLong.validate(uiState.value.taskDesc.trim())
        }

        var valResultCommits: ValidatorResult = ValidatorResult.Success
        if(!uiState.value.taskCommits.trim().isNullOrBlank()) {
            valResultCommits = chainTxtLong.validate(uiState.value.taskCommits.trim())
        }

        var valResultBranches: ValidatorResult = ValidatorResult.Success
        if(!uiState.value.taskBranches.trim().isNullOrBlank()) {
            valResultBranches = chainTxtLong.validate(uiState.value.taskBranches.trim())
        }

        var valResultNotes: ValidatorResult = ValidatorResult.Success
        if(!uiState.value.taskNotes.trim().isNullOrBlank()) {
            valResultNotes = chainTxtLong.validate(uiState.value.taskNotes.trim())
        }

        var valResultTime: ValidatorResult = ValidatorResult.Success
        if(uiState.value.taskDateReq != 0L && uiState.value.taskDateEnd != 0L) {
            valResultTime = dateValidator.validate(DateTimeUtils.dateToYear(uiState.value.taskDateReq),
                                                   DateTimeUtils.dateToYear(uiState.value.taskDateEnd),
                                                   DateTimeUtils.dateToMonth(uiState.value.taskDateReq),
                                                   DateTimeUtils.dateToMonth(uiState.value.taskDateEnd),
                                                   DateTimeUtils.dateToDay(uiState.value.taskDateReq),
                                                   DateTimeUtils.dateToDay(uiState.value.taskDateEnd))
        }

        if(uiState.value.taskStateSelectedList.size == 0) {
            result = ValidationResult(false, "States", "You must select one State at least")
        }

        if(valResultName is ValidatorResult.Error) {
            result = ValidationResult(false, "Name", "Field Name required")
            logger.info { "validateForm -> validate (Name) ERR: ${valResultName.message}" }
        }
        if(valResultJira is ValidatorResult.Error) {
            result = ValidationResult(false, "Jira", "Field Jira Must be 0 length or more than 5 length")
            logger.info { "validateForm -> validate (Jira) ERR: ${valResultJira.message}" }
        }
        if(valResultDesc is ValidatorResult.Error) {
            result = ValidationResult(false, "Description", "Field Description Must be 0 length or more than 5 length")
            logger.info { "validateForm -> validate (Desc) ERR: ${valResultDesc.message}" }
        }
        if(valResultCommits is ValidatorResult.Error) {
            result = ValidationResult(false, "Commits", "Field Commits Must be 0 length or more than 5 length")
            logger.info { "validateForm -> validate (Commits) ERR: ${valResultCommits.message}" }
        }
        if(valResultBranches is ValidatorResult.Error) {
            result = ValidationResult(false, "Branches", "Field Branches Must be 0 length or more than 5 length")
            logger.info { "validateForm -> validate (Branches) ERR: ${valResultBranches.message}" }
        }
        if(valResultNotes is ValidatorResult.Error) {
            result = ValidationResult(false, "Notes", "Field Notes Must be 0 length or more than 5 length")
            logger.info { "validateForm -> validate (Notes) ERR: ${valResultNotes.message}" }
        }
        if(valResultTime is ValidatorResult.Error) {
            result = ValidationResult(false, "Dates", "The start date must be before the end date ")
            logger.info { "validateForm -> validate (Time) ERR: ${valResultTime.message}" }
        }

        return result
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