package es.alfred.kmanager.view.page.tasking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.components.TaskComponentError
import es.alfred.kmanager.view.page.tasking.components.TasksComponentTitle
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailActionsRow
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailStatesRow
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailsForm
import es.alfred.kmanager.view.page.tasking.sections.TasksStateModeEnum
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
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
                   onNavigate: (String) -> Unit,
                   viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val title = remember { mutableStateOf("No Task") }

        if(title.value == "No Task") {
            viewModel.setStateMode(stateMode)
            title.value = uiState.title
        }
        TasksComponentTitle.show(uiState.title)

        tasksDetailActionsRow.showRow(onNavigate)
        if(uiState.generalError) {
            TaskComponentError.show(uiState.generalErrorText)
        }
        tasksDetailStatesRow.showRow()
        tasksDetailsForm.showRow()
    }
}