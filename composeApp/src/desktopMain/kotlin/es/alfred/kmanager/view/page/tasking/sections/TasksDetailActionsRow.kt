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
import es.alfred.kmanager.view.page.tasking.components.TasksComponentButton
import es.alfred.kmanager.view.page.tasking.components.TasksComponentSelect
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
import es.alfred.kmanager.view.shared.ComponentsConfDataFactory
import es.alfred.kmanager.view.shared.Navigation
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDetailActionsRow {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showSection(onNavigate: (String) -> Unit,
                    viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                projectListRow(viewModel)//Row
            }

            Column(
                Modifier
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start)
            {
                buttonsRow(viewModel, onNavigate)//Row
            }
        }//Row
    }

    @Composable
    private fun projectListRow(viewModel: TasksDetailViewModel) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val conf = ComponentsConfDataFactory.standardSelectConf(
                "Project",
                "Project",
                uiState.taskProjectList
            )
            if (viewModel.uiState.value.currentProject != null) {
                conf.defaultValue = uiState.currentProject!!
            }
            TasksComponentSelect.show(conf,
                                      onSelectChange = {
                                          viewModel.selectProject(it)
                                      })
        }
    }

    @Composable
    private fun buttonsRow(viewModel: TasksDetailViewModel, onNavigate: (String) -> Unit) {
        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TasksComponentButton.show(
                "Save",
                Color(0xFF336699),
                110.dp,
                onClick = {
                    viewModel.save()
                })

            Spacer(Modifier.width(20.dp))
            TasksComponentButton.show(
                "Cancel",
                Color(0xFFe51d2e),
                110.dp,
                onClick = {
                    onNavigate(Navigation.TASKVIEW_LIST)
                })

            Spacer(Modifier.width(20.dp))
        }
    }
}