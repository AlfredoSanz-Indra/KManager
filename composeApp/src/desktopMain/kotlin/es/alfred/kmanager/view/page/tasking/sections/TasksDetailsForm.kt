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
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
import es.alfred.kmanager.view.shared.ComponentsConfDataFactory
import es.alfred.kmanager.view.shared.DialogDatePickerView
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDetailsForm {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showSection(viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val confName = ComponentsConfDataFactory.standardTextConf("", "Name")
        confName.width = 0.45f
        val confJira = ComponentsConfDataFactory.standardTextConf("", "Jira")
        confJira.width = 0.81f
        rowOfText2(confName,
                   confJira,
                   onValueChangeA = {
                       viewModel.updateTaskName(it)
                   },
                   onValueChangeB = {
                       viewModel.updateTaskJira(it)
                   })

        val confDesc = ComponentsConfDataFactory.standardBigTextConf("", "Description")
        rowOfBigText(confDesc,
                     onValueChange = {
                        viewModel.updateTaskDesc(it)
                     })

        val confDateReq = ComponentsConfDataFactory.standardTextConf(uiState.taskDateReqFormatted, "Request date")
        confDateReq.width = 0.45f
        val confDateEnd = ComponentsConfDataFactory.standardTextConf(uiState.taskDateEndFormatted, "Finish date")
        confDateEnd.width = 0.81f
        rowOfDatepicker2(confDateReq,
                         confDateEnd,
                         onValueChangeA = {
                             viewModel.onDateReqSelected(it)
                         },
                         onValueChangeB = {
                             viewModel.onDateEndSelected(it)
                         })

        val confBranches = ComponentsConfDataFactory.standardBigTextConf("", "Branches")
        confBranches.minLines = 1
        confBranches.maxLines = 2
        rowOfBigText(confBranches,
                     onValueChange = {
                        viewModel.updateTaskBranches(it)
                     })

        val confCommits = ComponentsConfDataFactory.standardBigTextConf("", "Commits")
        confBranches.minLines = 2
        confBranches.maxLines = 5
        rowOfBigText(confCommits,
                     onValueChange = {
                        viewModel.updateTaskCommits(it)
                     })

        val confNotes = ComponentsConfDataFactory.standardBigTextConf("", "Notes")
        rowOfBigText(confNotes,
                     onValueChange = {
                        viewModel.updateTaskNotes(it)
                     })
    }

    @Composable
    private fun rowOfBigText(conf: TasksComponentBigTextConf, onValueChange: (String) -> Unit) {
        Spacer(Modifier.height(5.dp).background(color = Color(0xFFf7f6ff)).fillMaxWidth())
        Row(Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))

            TasksComponentBigText.show(conf,
                                       onValueChange = {
                                           onValueChange(it)
                                       })
        }
    }

    @Composable
    private fun rowOfText2(confA: TasksComponentTextConf,
                           confB: TasksComponentTextConf,
                           onValueChangeA: (String) -> Unit,
                           onValueChangeB: (String) -> Unit) {
        Spacer(Modifier.height(5.dp).background(color = Color(0xFFf7f6ff)).fillMaxWidth())

        Row(Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))

            TasksComponentText.show(confA,
                                    onValueChange = {
                                        onValueChangeA(it)
                                    })

            Spacer(Modifier.width(10.dp))

            TasksComponentText.show(confB,
                                    onValueChange = {
                                        onValueChangeB(it)
                                    })
        }
    }

    @Composable
    private fun rowOfDatepicker2(confA: TasksComponentTextConf,
                                 confB: TasksComponentTextConf,
                                 onValueChangeA: (Long) -> Unit,
                                 onValueChangeB: (Long) -> Unit,
                                 viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Spacer(Modifier.height(5.dp).background(color = Color(0xFFf7f6ff)).fillMaxWidth())

        Row(Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))

            TaskComponentTextDatepicker.show(confA,
                                             onOpenDatepicker = {
                                                 viewModel.updateShowTaskDateReqDialog(true)
                                             },
                                             onDateDeleted = {
                                                 onValueChangeA(0L)
                                             })

            Spacer(Modifier.width(10.dp))

            TaskComponentTextDatepicker.show(confB,
                                             onOpenDatepicker = {
                                                 viewModel.updateShowTaskDateEndDialog(true)
                                             },
                                             onDateDeleted = {
                                                 onValueChangeB(0L)
                                             })
        }

        if(uiState.showTaskDateReqDialog) {
            var temporalDate = 0L
            if(uiState.taskDateReq == 0L) {
                temporalDate = viewModel.getCurrentDate()
            }
            DialogDatePickerView.show(uiState.taskDateReq,
                                      temporalDate,
                                      onClose = {
                                          viewModel.updateShowTaskDateReqDialog(false)
                                      },
                                      onDateSelected = {
                                          onValueChangeA(it)
                                      })
        }
        if(uiState.showTaskDateEndDialog) {
            var temporalDate = 0L
            if(uiState.taskDateEnd == 0L) {
                temporalDate = viewModel.getCurrentDate()
            }
            DialogDatePickerView.show(uiState.taskDateEnd,
                                      temporalDate,
                                      onClose = {
                                          viewModel.updateShowTaskDateEndDialog(false)
                                      },
                                      onDateSelected = {
                                          onValueChangeB(it)
                                      })
        }
    }
}