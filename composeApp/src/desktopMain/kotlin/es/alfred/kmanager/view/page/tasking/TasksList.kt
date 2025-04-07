package es.alfred.kmanager.view.page.tasking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.sections.TasksListCards
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
    private val taskListCards: TasksListCards = TasksListCards()

    @Composable
    fun createPage(onNavigate: (String) -> Unit,
                   flag: Boolean,
                   viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {

        logger.info { "TasksList -> cretePage" }
        val isInitialized = remember { mutableStateOf(false) }

        if(flag != isInitialized.value) {
            viewModel.init()
        }

        Column(
            Modifier
                .background(color = Color(0xFFf7f6ff))
        ) {
            taskListSearch.showSection(onNavigate)

            taskListCards.showSection(onNavigate)
        }
    }
}