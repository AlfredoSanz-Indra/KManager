package es.alfred.kmanager.view.page.tasking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.sections.TasksListSearch
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksListViewModel
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksList() {

    private val logger = KotlinLogging.logger {}
    private val taskListSearch: TasksListSearch = TasksListSearch()

    @Composable
    fun createPage(viewModel: TasksListViewModel = viewModel { TasksListViewModel() },
                   onNavigate: (String) -> Unit) {

        logger.info { "cretePage" }
        logger.info { "showRow -> list: ${viewModel.uiState.value.taskStateList}" }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        taskListSearch.showRow(onNavigate)
    }
}