package es.alfred.kmanager.view.page.tasking.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import es.alfred.kmanager.view.page.tasking.components.*
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksDetailViewModel
import es.alfred.kmanager.view.shared.DialogDatePickerView
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksDetailsForm {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showRow(viewModel: TasksDetailViewModel = viewModel { TasksDetailViewModel() }) {
        logger.info { "showRow" }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val confName = standardTextConf("", "Name")
        confName.width = 0.45f
        val confJira = standardTextConf("", "Jira")
        confJira.width = 0.81f
        rowOfText2(confName,
                   confJira,
                   onValueChangeA = {
                       viewModel.updateTaskName(it)
                   },
                   onValueChangeB = {
                       viewModel.updateTaskJira(it)
                   })

        val confDesc = standardBigTextConf("", "Description")
        rowOfText(confDesc,onValueChange = {
                               viewModel.updateTaskDesc(it)
                           })

        val confDateReq = standardTextConf("", "Request date")
        confDateReq.width = 0.45f
        val confDateEnd = standardTextConf("", "FinishDate")
        confDateEnd.width = 0.81f
        rowOfDatepicker2(confDateReq,
                         confDateEnd,
                         onValueChangeA = {
                             viewModel.onDateReqSelected(it)
                         },
                         onValueChangeB = {
                             viewModel.onDateEndSelected(it)
                         })

        val confBranches = standardBigTextConf("", "Branches")
        confBranches.minLines = 1
        confBranches.maxLines = 2
        rowOfText(confBranches, onValueChange = {
                                    viewModel.updateTaskBranches(it)
                                })

        val confCommits = standardBigTextConf("", "Commits")
        confBranches.minLines = 2
        confBranches.maxLines = 5
        rowOfText(confCommits, onValueChange = {
                                  viewModel.updateTaskCommits(it)
                              })

        val confNotes = standardBigTextConf("", "Notes")
        rowOfText(confNotes, onValueChange = {
                                 viewModel.updateTaskNotes(it)
                             })

    }

    @Composable
    private fun rowOfText(conf: TasksComponentBigTextConf, onValueChange: (String) -> Unit) {
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
                                             uiState.taskDateReqFormatted,
                                             onOpenDatepicker = {
                                                 logger.info { "rowOfDatepicker2 -> Req, onOpenDatepicker" }
                                                 viewModel.updateShowTaskDateReqDialog(true)
                                             })

            Spacer(Modifier.width(10.dp))

            TaskComponentTextDatepicker.show(confB,
                                             uiState.taskDateEndFormatted,
                                             onOpenDatepicker = {
                                                 viewModel.updateShowTaskDateEndDialog(true)
                                                 logger.info { "rowOfDatepicker2 -> End, onOpenDatepicker" }
                                             })
        }

        logger.info { "rowOfDatepicker2 ->  uiState.showTaskDateReqDialog: ${uiState.showTaskDateReqDialog}" }
        logger.info { "rowOfDatepicker2 ->  uiState.showTaskDateEndDialog: ${uiState.showTaskDateEndDialog}" }
        if(uiState.showTaskDateReqDialog) {
            DialogDatePickerView.show(uiState.taskDateReq,
                                      onClose = {
                                          logger.info { "rowOfDatepicker2 ->  onClose" }
                                          viewModel.updateShowTaskDateReqDialog(false)
                                      },
                                      onDateSelected = {
                                          logger.info { "rowOfDatepicker2 -> Req, onDateSelected: $it" }
                                          onValueChangeA(it)
                                      })
        }
        if(uiState.showTaskDateEndDialog) {
            DialogDatePickerView.show(uiState.taskDateEnd,
                                      onClose = {
                                          logger.info { "rowOfDatepicker2 ->  onClose" }
                                          viewModel.updateShowTaskDateEndDialog(false)
                                      },
                                      onDateSelected = {
                                          logger.info { "rowOfDatepicker2 -> End, onDateSelected: $it" }
                                          onValueChangeB(it)
                                      })
        }
    }

    @Composable
    private fun standardTextConf(initialText: String, label: String): TasksComponentTextConf {
        return TasksComponentTextConf(initialText, label,0.9f, 65.dp, 14.sp,false, 100)
    }

    @Composable
    private fun standardBigTextConf(initialText: String, label: String): TasksComponentBigTextConf {
        return TasksComponentBigTextConf(initialText, label,0.9f, 65.dp, 14.sp,false, 1000, 5, 3)
    }
}