package es.alfred.kmanager.view.page.tasking

import androidx.compose.runtime.Composable
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksListViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.sections.TasksListSearch
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksList() {

    private val logger = KotlinLogging.logger {}
    private val taskListSearch: TasksListSearch = TasksListSearch()

    @Composable
    fun createPage(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        logger.info { "cretePage" }
        logger.info { "createPage -> list: ${viewModel.uiState.value.taskStateList}" }

        taskListSearch.showRow()
    }
}