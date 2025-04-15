package es.alfred.kmanager.view.page.tasking.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.components.TasksComponentCardTask
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksListViewModel
import es.alfred.kmanager.view.shared.KManagerDialog
import es.alfred.kmanager.view.shared.Navigation
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksListCards {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showSection(onNavigate: (String, String) -> Unit,
                    viewModel: TasksListViewModel = viewModel { TasksListViewModel() }
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Column(
            Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFf7f6ff))
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFf7f6ff))
                    .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    items(uiState.tasksList.size,
                          itemContent = { item ->

                        val thetask = uiState.tasksList[item]
                        TasksComponentCardTask.show(thetask,
                                                    onEdit = {
                                                        onNavigate(Navigation.TASKVIEW_UPDATE, thetask.id!!)
                                                    },
                                                    onDelete = {
                                                        viewModel.showConfirmDelete(thetask.id!!)
                                                    })
                    })
                }//lazy
            }//Box
        }//column

        dialogDelete()
    }

    @Composable
    private fun dialogDelete(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        if(uiState.flagShowConfirmDelete) {
            KManagerDialog.confirmDialog("Proceed with Delete Task?",
                uiState.flagShowConfirmDelete,
                onAccept = {
                    viewModel.hideConfirmDelete()
                    viewModel.deleteTask()
                },
                onDecline = {
                    viewModel.hideConfirmDelete()
                })
        }
    }
}