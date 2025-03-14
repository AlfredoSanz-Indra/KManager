package es.alfred.kmanager.view.page.tasking.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.components.TaskComponentError
import es.alfred.kmanager.view.page.tasking.components.TasksComponentButton
import es.alfred.kmanager.view.page.tasking.components.TasksComponentChip
import es.alfred.kmanager.view.page.tasking.components.TasksComponentText
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksListViewModel
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksListSearch {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showRow(onNavigate: (String) -> Unit,
                viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {

        logger.info { "showRow -> list: ${viewModel.uiState.value.taskStateList}" }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Row(
            Modifier
            .background(color = Color(0xFFf7f6ff))
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(25.dp))
            for(item in uiState.taskStateList) {
                TasksComponentChip.show(item,
                                            onSelectedChip = {
                                                viewModel.addTaskStateToSearchList(it)
                                            })
            }
        }

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            TasksComponentText.show("",
                                    "Search text",
                                    0.60f,
                                    onValueChange = {
                                        viewModel.updateTaskFieldSearch(it)
                                    })

            Spacer(Modifier.width(20.dp))
            TasksComponentButton.show("Search",
                                      Color(0xFF336699),
                                      110.dp,
                                      onClick = {
                                          viewModel.search()
                                      } )

            Spacer(Modifier.width(20.dp))
            TasksComponentButton.show("New",
                                       Color(0xFFe51d2e),
                                       110.dp,
                                       onClick = {
                                           onNavigate("new")
                                       } )
        }//Row

        if(uiState.generalError) {
            TaskComponentError.show(uiState.generalErrorText)
        }
    }
}