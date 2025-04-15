package es.alfred.kmanager.view.page.tasking

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.components.TaskComponentError
import es.alfred.kmanager.view.page.tasking.components.TasksComponentTitle
import es.alfred.kmanager.view.page.tasking.model.TasksStateModeEnum
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailActionsRow
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailStatesRow
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailsForm
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
import es.alfred.kmanager.view.shared.Navigation
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDetail {

    private val logger = KotlinLogging.logger {}
    private val tasksDetailActionsRow: TasksDetailActionsRow = TasksDetailActionsRow()
    private val tasksDetailStatesRow: TasksDetailStatesRow = TasksDetailStatesRow()
    private val tasksDetailsForm: TasksDetailsForm = TasksDetailsForm()

    @Composable
    fun createPage(stateMode: TasksStateModeEnum,
                   taskId: String,
                   onNavigate: (String) -> Unit,
                   flag: Boolean,
                   viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {

        val uiState by viewModel.uiState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.STARTED)
        val isInitialized = remember { mutableStateOf(false) }

        if(flag != isInitialized.value) {
            viewModel.init()
            viewModel.setStateMode(stateMode, taskId)
            uiState.saveAction = false
            isInitialized.value = flag
        }

        TasksComponentTitle.show(uiState.title)
        if(uiState.saveAction == true) {
            onNavigate(Navigation.TASKVIEW_LIST)
        }
        tasksDetailActionsRow.showSection(onNavigate)
        if(uiState.generalError) {
            Spacer(modifier = Modifier.width(20.dp))
            TaskComponentError.showRow(uiState.generalErrorText)
        }
        tasksDetailStatesRow.showSection()
        tasksDetailsForm.showSection()
    }
}