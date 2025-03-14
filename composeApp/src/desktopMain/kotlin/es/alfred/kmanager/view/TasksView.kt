package es.alfred.kmanager.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import es.alfred.kmanager.view.page.tasking.TasksDetail
import es.alfred.kmanager.view.page.tasking.TasksList
import es.alfred.kmanager.view.shared.KHeaderMenu
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksView() : IView {
    private val logger = KotlinLogging.logger {}
    private val kheaderMenu: KHeaderMenu = KHeaderMenu()
    private val tasksList: TasksList = TasksList()
    private val tasksDetail: TasksDetail = TasksDetail()

    @Composable
    override fun createView(onChangeView: (String) -> Unit) {
        logger.info { "createView" }
        var showView: String by remember { mutableStateOf("list") }

        MaterialTheme(darkColorScheme(background = Color.Black)) {
            Column {
                kheaderMenu.createPage(onChangeView)

                when (showView) {
                    "new" -> tasksDetail.createPage("new", onNavigate = {
                        showView = it
                    })

                    "update" -> tasksDetail.createPage("update", onNavigate = {
                        showView = it
                    })

                    "list" -> tasksList.createPage(onNavigate = {
                        showView = it
                    })
                }
            }
        }
    }
}