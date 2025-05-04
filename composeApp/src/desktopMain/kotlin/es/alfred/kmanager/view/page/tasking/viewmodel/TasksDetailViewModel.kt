package es.alfred.kmanager.view.page.tasking.viewmodel

import androidx.lifecycle.ViewModel
import es.alfred.kmanager.core.di.UseCaseFactory
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.core.util.DateTimeUtils
import es.alfred.kmanager.core.validators.ChainTextValidator
import es.alfred.kmanager.core.validators.DateGreaterValidator
import es.alfred.kmanager.core.validators.TextValidatorLength
import es.alfred.kmanager.core.validators.ValidatorResult
import es.alfred.kmanager.domain.model.SelectData
import es.alfred.kmanager.domain.model.Task
import es.alfred.kmanager.view.context.TasksContext
import es.alfred.kmanager.view.page.tasking.model.TasksStateModeEnum
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
data class TasksDetailUiState(
    val taskStateList: List<String> = listOf("Pending", "Working", "Stopped", "Waiting", "Pushed", "Closed"),
    val taskStateSelectedList: MutableList<String> = mutableListOf(),
    val taskProjectList: List<SelectData> = mutableListOf(),
    var currentProject: SelectData? = null,
    var taskName: String = "",
    var taskJira: String = "",
    var taskDesc: String = "",
    var taskNotes: String = "",
    var taskBranches: String = "",
    var taskCommits: String = "",
    var taskDateReq: Long = 0L,
    var taskDateReqFormatted: String = "",
    var taskDateEnd: Long = 0L,
    var taskDateEndFormatted: String = "",
    var taskCreationDate: Long = 0L,
    var generalError: Boolean = false,
    var generalErrorText: String = "",
    val title: String = "No Task",
    var mode: Int = 1,
    var saveAction: Boolean = false,
    var showTaskDateReqDialog: Boolean = false,
    var showTaskDateEndDialog: Boolean = false,
    var searching: Boolean = false,
    var saving: Boolean = false,
    var checkLoadedTask: Boolean = false,
    var editingTaskID: String? = null
)

val taskShort_MAXLENGTH = 100
val taskLong_MAXLENGTH = 1000

class TasksDetailViewModel: ViewModel(){
    private val logger = KotlinLogging.logger {}
    private val _uiState = MutableStateFlow(TasksDetailUiState())
    val uiState: StateFlow<TasksDetailUiState> = _uiState.asStateFlow()

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
    }

    fun setStateMode(stateMode: TasksStateModeEnum, taskId: String?) {
        this.updateMode(stateMode.stateMode)
        when(stateMode) {
            TasksStateModeEnum.NEW_TASK -> creatingStateModeInit()
            else -> updatingStateModeInit(taskId!!)
        }
    }

    fun selectProject(project: SelectData) {
        updateCurrentProject(project)
    }

    private fun creatingStateModeInit() {
        updateTitle("New Task")
        updateEditingTaskID(null)
        updateTaskDateReq(DateTimeUtils.currentDate(), DateTimeUtils.currentDateFormatted())
    }

    private fun updatingStateModeInit(taskId: String) {
        updateTitle("Edit Task")
        getTask(taskId)
        updateEditingTaskID(taskId)
    }

    fun addTaskStateToSelectedList(taskState: String) {
        when(taskState.startsWith("N-")) {
            true -> _uiState.value.taskStateSelectedList.remove(taskState.substring(2))
            false -> _uiState.value.taskStateSelectedList.addLast(taskState)
        }
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
        val tasksUseCase = UseCaseFactory.getTasksUseCase()

        val validateResult = validateForm()
        if(!validateResult.result) {
            logger.info { "save -> Error validating: ${validateResult.message}" }
            updateGeneralError(true, "The field ${validateResult.field} ${validateResult.message}")
            return
        }

        updateSaving(true)
        val task: Task = createTaskObj()
        logger.info { "save -> creationDate: ${task.creationDate}" }
        CoroutineScope(Dispatchers.IO).launch {
            if(uiState.value.mode == TasksStateModeEnum.UPDATE_TASK.stateMode ) {
                task.id = uiState.value.editingTaskID
            }
            val result = tasksUseCase.saveTask(task)
            updateSaving(false)
            if(result.result) {
                updateSaveAction(true)
                updateEditingTaskID(null)
            }
            else {
                updateGeneralError(true, result.errorMsg!!)
            }
        }
    }

    private fun createTaskObj(): Task {
        val result = Task(
            null,
            uiState.value.currentProject!!.name,
            uiState.value.taskStateSelectedList,
            uiState.value.taskName,
            if(uiState.value.taskJira.isNotBlank()) uiState.value.taskJira.trim() else null,
            if(uiState.value.taskDesc.isNotBlank()) uiState.value.taskDesc.trim() else null,
            if(uiState.value.taskNotes.isNotBlank()) uiState.value.taskNotes.trim() else null,
            if(uiState.value.taskBranches.isNotBlank()) uiState.value.taskBranches.trim() else null,
            if(uiState.value.taskCommits.isNotBlank())uiState.value.taskCommits.trim() else null,
            if(uiState.value.taskDateReq != 0L) uiState.value.taskDateReq else null,
            if(uiState.value.taskDateEnd != 0L) uiState.value.taskDateEnd else null,
            if(uiState.value.taskDateReq != 0L) DateTimeUtils.dateToYear(uiState.value.taskDateReq) else null,
            if(uiState.value.taskDateReq != 0L) DateTimeUtils.dateToMonth(uiState.value.taskDateReq) else null,
            if(uiState.value.taskDateReq != 0L) DateTimeUtils.dateToDay(uiState.value.taskDateReq) else null,
            if(uiState.value.taskDateEnd != 0L) DateTimeUtils.dateToYear(uiState.value.taskDateEnd) else null,
            if(uiState.value.taskDateEnd != 0L) DateTimeUtils.dateToMonth(uiState.value.taskDateEnd) else null,
            if(uiState.value.taskDateEnd != 0L) DateTimeUtils.dateToDay(uiState.value.taskDateEnd) else null,
            if(uiState.value.taskCreationDate != 0L) uiState.value.taskCreationDate else DateTimeUtils.currentDateTime(),
        )

        return result
    }

    private fun validateForm(): ValidationResult {
        var result = ValidationResult(true, "", "")
        clearErrors()

        //----VALIDATORS----
        val chainTxtShort = ChainTextValidator(
            TextValidatorLength(5, taskShort_MAXLENGTH)
        )
        val chainTxtLong = ChainTextValidator(
            TextValidatorLength(5, taskLong_MAXLENGTH)
        )
        val dateValidator = DateGreaterValidator()

        //----VALIDATIONS----
        val valResultName = chainTxtShort.validate(uiState.value.taskName.trim())

        var valResultJira: ValidatorResult = ValidatorResult.Success
        if(uiState.value.taskJira.isNotBlank()) {
            valResultJira = chainTxtShort.validate(uiState.value.taskJira.trim())
        }

        var valResultDesc: ValidatorResult = ValidatorResult.Success
        if(uiState.value.taskDesc.trim().isNotBlank()) {
            valResultDesc = chainTxtLong.validate(uiState.value.taskDesc.trim())
        }

        var valResultCommits: ValidatorResult = ValidatorResult.Success
        if(uiState.value.taskCommits.trim().isNotBlank()) {
            valResultCommits = chainTxtLong.validate(uiState.value.taskCommits.trim())
        }

        var valResultBranches: ValidatorResult = ValidatorResult.Success
        if(uiState.value.taskBranches.trim().isNotBlank()) {
            valResultBranches = chainTxtLong.validate(uiState.value.taskBranches.trim())
        }

        var valResultNotes: ValidatorResult = ValidatorResult.Success
        if(uiState.value.taskNotes.trim().isNotBlank()) {
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

        var valResultProject: ValidatorResult = ValidatorResult.Success
        if(uiState.value.currentProject == null) {
            valResultProject = ValidatorResult.Error("Project")
        }


        //----CHECK VALIDATION RESULTS----
        if(valResultProject is ValidatorResult.Error) {
            result = ValidationResult(false, "Project", "must be selected")
        }
        if(uiState.value.taskStateSelectedList.size == 0) {
            result = ValidationResult(false, "States", "must have at least one chip selected")
        }
        if(valResultName is ValidatorResult.Error) {
            result = ValidationResult(false, "Name", valResultName.message)
        }
        if(valResultJira is ValidatorResult.Error) {
            result = ValidationResult(false, "Jira", valResultJira.message)
        }
        if(valResultDesc is ValidatorResult.Error) {
            result = ValidationResult(false, "Description", valResultDesc.message)
        }
        if(valResultCommits is ValidatorResult.Error) {
            result = ValidationResult(false, "Commits", valResultCommits.message)
        }
        if(valResultBranches is ValidatorResult.Error) {
            result = ValidationResult(false, "Branches", valResultBranches.message)
        }
        if(valResultNotes is ValidatorResult.Error) {
            result = ValidationResult(false, "Notes", valResultNotes.message)
        }
        if(valResultTime is ValidatorResult.Error) {
            result = ValidationResult(false, "Dates", valResultTime.message)
        }

        return result
    }

    private fun getTask(taskId: String) {
        logger.info { "getTask -> taskId: $taskId" }
        val tasksUseCase = UseCaseFactory.getTasksUseCase()

        updateSearching(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resp = tasksUseCase.getTask(taskId)
                if (resp.result) {
                    mapToForm(resp.task!!)
                    logger.info { "getTask -> task.creationDate: ${uiState.value.taskCreationDate}" }
                    updateCheckLoadedTask(true)
                }
            }
            catch (err: Error) {
                updateGeneralError(true, "Error getting Task")
                logger.info { "Error Getting: ${err.message}" }
            }
            finally {
                updateSearching(false)
            }
        }
    }

    private fun mapToForm(task: Task) {
        updateTaskName(task.name)
        updateTaskJira(task.jira ?: "")
        updateTaskDesc(task.description ?: "")
        updateTaskBranches(task.branches ?: "")
        updateTaskCommits(task.commits ?: "")
        updateTaskNotes(task.notes ?: "")
        if(task.dateReq != null) {
            val dateFormatted = calcDateFormatted(task.dateReq!!)
            updateTaskDateReq(task.dateReq!!, dateFormatted)
        }
        if(task.dateEnd != null) {
            val dateFormatted = calcDateFormatted(task.dateEnd!!)
            updateTaskDateEnd(task.dateEnd!!, dateFormatted)
        }
        _uiState.value.taskStateSelectedList.addAll(task.states)
        _uiState.value.taskCreationDate = task.creationDate
    }

    private fun updateTaskProjectList(projectList: List<SelectData>) {
        _uiState.update {
            it.copy(taskProjectList = projectList)
        }
    }

    private fun updateCurrentProject(currentProject: SelectData?) {
        _uiState.update {
            it.copy(currentProject = currentProject)
        }
    }

    private fun updateMode(num: Int) {
        _uiState.update {
            it.copy(mode = num)
        }
    }

    private fun updateTitle(txt: String) {
        _uiState.update {
            it.copy(title = txt)
        }
    }

    fun updateTaskName(txt: String) {
        _uiState.update {
            it.copy(taskName = txt)
        }
    }
    fun updateTaskJira(txt: String) {
        _uiState.update {
            it.copy(taskJira = txt)
        }
    }
    fun updateTaskDesc(txt: String) {
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
        _uiState.update {
            it.copy(showTaskDateReqDialog = action)
        }
    }
    fun updateShowTaskDateEndDialog(action: Boolean) {
        _uiState.update {
            it.copy(showTaskDateEndDialog = action)
        }
    }
    fun updateTaskNotes(txt: String) {
        _uiState.update {
            it.copy(taskNotes = txt)
        }
    }
    fun updateTaskBranches(txt: String) {
        _uiState.update {
            it.copy(taskBranches = txt)
        }
    }
    fun updateTaskCommits(txt: String) {
        _uiState.update {
            it.copy(taskCommits = txt)
        }
    }

    private fun updateSaveAction(action: Boolean) {
        _uiState.update {
            it.copy(saveAction = action)
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

    private fun updateSearching(action: Boolean) {
        _uiState.update {
            it.copy(searching = action)
        }
    }

    private fun updateSaving(action: Boolean) {
        _uiState.update {
            it.copy(saving = action)
        }
    }

    fun updateCheckLoadedTask(value: Boolean) {
        _uiState.update {
            it.copy(checkLoadedTask = value)
        }
    }

    private fun updateEditingTaskID(id: String?) {
        _uiState.update {
            it.copy(editingTaskID = id)
        }
    }

    private fun clearState() {
        updateSaveAction(false)
        updateEditingTaskID(null)
        updateSearching(false)
        updateSaving(false)
        updateCheckLoadedTask(false)
        updateTitle("")
        _uiState.value.taskStateSelectedList.clear()
        updateCurrentProject(null)
        updateTaskProjectList(mutableListOf())
        updateTaskName("")
        updateTaskCommits("")
        updateTaskNotes("")
        updateTaskDesc("")
        updateTaskDateReq(0, "")
        updateTaskDateEnd(0, "")
        updateTaskJira("")
        updateTaskBranches("")
        clearErrors()
    }

    private fun clearErrors() {
        updateGeneralError(false, "")
    }
}