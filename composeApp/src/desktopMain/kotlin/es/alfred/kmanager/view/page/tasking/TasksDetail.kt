package es.alfred.kmanager.view.page.tasking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.components.TaskComponentError
import es.alfred.kmanager.view.page.tasking.components.TasksComponentTitle
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailActionsRow
import es.alfred.kmanager.view.page.tasking.sections.TasksDetailStatesRow
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

    @Composable
    fun createPage(mode: String,
                   onNavigate: (String) -> Unit,
                   viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {

        logger.info { "showRow -> list: ${viewModel.uiState.value.taskStateList}" }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        viewModel.updateMode(mode)

        TasksComponentTitle.show(getTitle())

        tasksDetailActionsRow.showRow(onNavigate)
        tasksDetailStatesRow.showRow()

        if(uiState.generalError) {
            TaskComponentError.show(uiState.generalErrorText)
        }
    }

    @Composable
    private fun getTitle(viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }): String {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        return if(uiState.mode == "new") {
            "New Task"
        }
        else {
            "Update Task"
        }
    }
}