package es.alfred.kmanager.view.page.tasking.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.view.page.tasking.components.TasksComponentButton
import es.alfred.kmanager.view.page.tasking.components.TasksComponentSelect
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
import es.alfred.kmanager.view.shared.ComponentsConfDataFactory
import es.alfred.kmanager.view.shared.SelectData
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDetailActionsRow {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showSection(onNavigate: (String) -> Unit,
                    viewModel: TasksDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel { TasksDetailViewModel() }) {
        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val projectList: List<SelectData> = TheResources.getResources().projects.map { SelectData(it.name, it.label) }

            Column(
                Modifier
                    .fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    Modifier
                        .background(color = Color(0xFFf7f6ff))
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val conf = ComponentsConfDataFactory.standardSelectConf("Project", "Project", projectList)
                    TasksComponentSelect.show(conf, onSelectChange = {
                        logger.info { "details -> onValueChange: $it" }
                    })
                }//Row
            }

            Column(
                Modifier
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start)
            {
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
                            onNavigate("list")
                        })

                    Spacer(Modifier.width(20.dp))
                }//Row
            }
        }//Row
    }
}