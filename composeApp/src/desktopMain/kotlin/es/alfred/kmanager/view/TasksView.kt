package es.alfred.kmanager.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import es.alfred.kmanager.view.page.tasking.TasksDetail
import es.alfred.kmanager.view.page.tasking.TasksList
import es.alfred.kmanager.view.page.tasking.model.TasksStateModeEnum
import es.alfred.kmanager.view.shared.KHeaderMenu
import es.alfred.kmanager.view.shared.Navigation

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksView() : IView {
    private val kheaderMenu: KHeaderMenu = KHeaderMenu()
    private val tasksList: TasksList = TasksList()
    private val tasksDetail: TasksDetail = TasksDetail()

    @Composable
    override fun createView(onChangeView: (String) -> Unit) {
        var showView: String by remember { mutableStateOf(Navigation.TASKVIEW_LIST) }
        var taskId: String by remember { mutableStateOf("") }

        MaterialTheme(darkColorScheme(background = Color.Black)) {
            Column {
                kheaderMenu.createPage(onChangeView)

                when (showView) {
                    Navigation.TASKVIEW_NEW -> tasksDetail.createPage(TasksStateModeEnum.NEW_TASK,
                                                                      "",
                                                                      onNavigate = {
                                                                          showView = it
                                                                          taskId = ""
                                                                      },
                                                                     true)

                    Navigation.TASKVIEW_UPDATE -> tasksDetail.createPage(TasksStateModeEnum.UPDATE_TASK,
                                                                         taskId,
                                                                         onNavigate = {
                                                                             showView = it
                                                                         },
                                                                         true)

                    Navigation.TASKVIEW_LIST -> tasksList.createPage(onNavigate =  {showV, id ->
                                                                         showView = showV
                                                                         taskId = id
                                                                     },
                                                                     true)
                }
            }
        }
    }
}