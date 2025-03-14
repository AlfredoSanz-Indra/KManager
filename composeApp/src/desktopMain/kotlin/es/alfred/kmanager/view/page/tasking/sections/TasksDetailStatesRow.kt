package es.alfred.kmanager.view.page.tasking.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.components.TasksComponentChip
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDetailStatesRow {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showRow(viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {
        logger.info { "showRow" }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(25.dp))
            for (item in uiState.taskStateList) {
                TasksComponentChip.show(item,
                                            onSelectedChip = {
                                                viewModel.addTaskStateToSelectedList(it)
                                            })
            }
        }
    }
}