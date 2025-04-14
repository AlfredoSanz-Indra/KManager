package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.domain.model.Task
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksListViewModel

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksComponentCardTask {

    @Composable
    fun show(thetask: Task, onEdit: () -> Unit, onDelete: (String) -> Unit) {
        OutlinedCard(
            modifier = Modifier
                .padding(vertical = 3.dp)
                .fillMaxWidth()
                .height(100.dp)
                .waterfallPadding(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color(0xFF2a4481),
                contentColor = Color.Black,
                disabledContainerColor = Color.Blue,
                disabledContentColor = Color.Black
            ),
            elevation = CardDefaults.outlinedCardElevation(),
            border = BorderStroke(1.dp, Color.White),
        )
        {
            Column(
                Modifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            )
            {
                Spacer(modifier = Modifier.height(5.dp))
                rowOne(thetask)
                Spacer(modifier = Modifier.height(5.dp))
                rowTwo(thetask)
                Spacer(modifier = Modifier.height(5.dp))
                rowThree(thetask, onEdit, onDelete)
            } //Column
        } //card
    }

    @Composable
    private fun rowOne(thetask: Task) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal= 20.dp, vertical = 2.dp),
        ) {
            Column(modifier = Modifier
                                .padding(horizontal = 0.dp)
                                .fillMaxWidth(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start) {
                Text(
                    text = thetask.name,
                    style = TextStyle(
                        color = Color.White
                    ),
                )
            }

            Column(modifier =  Modifier
                                    .fillMaxWidth(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End) {
                Text(
                    text = thetask.states.toString(),
                    style = TextStyle(
                        color = Color.White
                    ),
                )
            }

        }
    }

    @Composable
    private fun rowTwo(thetask: Task) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal= 20.dp, vertical = 2.dp),
        ) {
            Column(modifier = Modifier
                                .padding(horizontal = 0.dp)
                                .fillMaxWidth(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start) {

                Text(
                    text = thetask.branches ?: "No branches declared",
                    style = TextStyle(
                        color = Color.White
                    ),
                )
            }

            Column(modifier = Modifier
                                 .fillMaxWidth(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = thetask.jira ?: "No Jira declared",
                    style = TextStyle(
                        color = Color.White
                    ),
                )
            }
        }
    }

    @Composable
    private fun rowThree(thetask: Task,
                         onEdit: () -> Unit,
                         onDelete: (String) -> Unit,
                         viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(horizontal= 20.dp, vertical = 2.dp),
        ) {
            Column(modifier = Modifier
                                .padding(horizontal = 0.dp)
                                .fillMaxWidth(0.6f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start) {
                Text(
                    text = viewModel.taskDateToString(thetask),
                    style = TextStyle(
                        color = Color.White
                    ),
                )
            }

            Column(modifier = Modifier
                                .fillMaxWidth(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End) {

                Row(horizontalArrangement = Arrangement.End,) {
                    TasksComponentCardButton.show(
                        "Edit",
                        Color(0xFF35682d),
                        90.dp,
                        Icons.Filled.Edit,
                        onClick = {
                            onEdit()
                        })

                    Spacer(Modifier.width(10.dp))

                    TasksComponentCardButton.show(
                        "Del",
                        Color(0xFFe51d2e),
                        100.dp,
                        Icons.Filled.DeleteForever,
                        onClick = {
                            onDelete(thetask.id!!)
                        })
                }
            }
        }
    }

}