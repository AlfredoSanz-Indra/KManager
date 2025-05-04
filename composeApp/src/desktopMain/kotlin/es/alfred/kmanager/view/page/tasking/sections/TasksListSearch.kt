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
import es.alfred.kmanager.view.page.tasking.components.*
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksListViewModel
import es.alfred.kmanager.view.shared.ComponentsConfDataFactory
import es.alfred.kmanager.view.shared.Navigation
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksListSearch {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showSection(onNavigate: (String, String) -> Unit,
                    viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        rowProjects()
        rowChips()
        rowTextAndActions(onNavigate)

        if(uiState.generalError) {
            TaskComponentError.showRow(uiState.generalErrorText)
        }
        if(uiState.searching) {
            TaskComponentExecutinActionMessage.showRow("Searching")
        }
        if(uiState.deleting) {
            TaskComponentExecutinActionMessage.showRow("Deleting Task")
        }
    }

    @Composable
    private fun rowProjects(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth()
                .padding(horizontal= 20.dp, vertical = 5.dp),
        ) {
            //double row because of the background color
            Row(
                Modifier
                    .background(color = Color(0xFFf7f6ff))
                    .fillMaxWidth(0.6f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val conf = ComponentsConfDataFactory.standardSelectConf("Project",
                                                                            "Project",
                                                                                 uiState.taskProjectList)
                if(uiState.currentProject != null) {
                    conf.defaultValue = uiState.currentProject!!
                }
                TasksComponentSelect.show(conf, onSelectChange = {
                    viewModel.changeCurrentProject(it)
                })
            }
        }//Row
    }

    @Composable
    private fun rowChips(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
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
                          uiState.taskStateSearchList.contains(item),
                                        onSelectedChip = {
                                            viewModel.addTaskStateToSearchList(it)
                                        })
            }
        }
    }

    @Composable
    private fun rowTextAndActions(onNavigate: (String, String) -> Unit,
                                  viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            val conf = ComponentsConfDataFactory.standardTextConf("","Search text")
            conf.width = 0.59f
            TasksComponentText.show(conf,
                onValueChange = {
                    viewModel.updateTaskFieldSearch(it)
                })

            Spacer(Modifier.width(30.dp))

            TasksComponentButton.show("Search",
                Color(0xFF336699),
                110.dp,
                onClick = {
                    viewModel.search()
                } )

            Spacer(Modifier.width(20.dp))

            TasksComponentButton.show("New",
                Color(0xFF35682d),
                110.dp,
                onClick = {
                    onNavigate(Navigation.TASKVIEW_NEW, "")
                } )
        }//Row
    }
}