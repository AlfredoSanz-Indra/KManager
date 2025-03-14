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
import es.alfred.kmanager.view.page.tasking.components.TasksComponentButton
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDetailActionsRow {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showRow(onNavigate: (String) -> Unit,
                viewModel: TasksDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel { TasksDetailViewModel() }) {
        logger.info { "showRow" }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            TasksComponentButton.show("Save",
                Color(0xFF336699),
                110.dp,
                onClick = {
                    viewModel.save()
                } )

            Spacer(Modifier.width(20.dp))
            TasksComponentButton.show("Cancel",
                Color(0xFFe51d2e),
                110.dp,
                onClick = {
                    onNavigate("list")
                } )
        }//Row
    }
}