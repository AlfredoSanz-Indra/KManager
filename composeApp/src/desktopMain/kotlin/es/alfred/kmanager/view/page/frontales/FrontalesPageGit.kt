package es.alfred.kmanager.view.page.frontales

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.di.UseCaseFactory
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.domain.usecaseapi.OperationsUseCase
import es.alfred.kmanager.view.page.frontales.section.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGit {
    private val logger = KotlinLogging.logger {}

    private val frontpageGitUpperRow: FrontalesPageGitUpperRow = FrontalesPageGitUpperRow()
    private val frontpageGitChipsRow: FrontalesPageGitChipsRow = FrontalesPageGitChipsRow()
    private val frontpageGitChipsButtonsRow: FrontalesPageGitChipsButtonsRow = FrontalesPageGitChipsButtonsRow()
    private val frontPageGitBranchControlsRow: FrontalesPageGitChipsBranchesRow = FrontalesPageGitChipsBranchesRow()
    private val frontpageGitChipCheckoutsRow: FrontalesPageGitChipsBranchesButtonsRow = FrontalesPageGitChipsBranchesButtonsRow()

    @Composable
    fun createPage() {
        var branchName by remember  { mutableStateOf("") }
        var flagUpdatedBranchList by remember { mutableStateOf(false) }
        var flagFirstTime by remember { mutableStateOf(true) }
        var flagChipSelected by remember { mutableStateOf(false) }
        val branches: MutableList<String> = remember { mutableStateListOf() }
        val chipsSelected: MutableMap<String, Boolean> = remember { mutableStateMapOf() }

        if(flagFirstTime) {
            getProjectsData(chipsSelected)
        }
        if(!flagFirstTime && flagUpdatedBranchList) {
            updateBranchesList(branches, chipsSelected, onFinish = {
                flagChipSelected = false
                flagUpdatedBranchList = false
            })
        }
        flagFirstTime = false

        if(flagChipSelected) {
            updateBranchesList(branches, chipsSelected, onFinish = {
                flagChipSelected = false
            })
        }

        Spacer(Modifier.height(20.dp))
        this.frontpageGitUpperRow.showRow()

        Spacer(Modifier.height(20.dp))
        this.frontpageGitChipsRow.showRow(chipsSelected,
                                             onChipSelected = {
                                                 flagChipSelected = true
                                             })

        Spacer(Modifier.height(20.dp))
        this.frontpageGitChipsButtonsRow.showRow(chipsSelected)

        Spacer(Modifier.height(20.dp))
        this.frontPageGitBranchControlsRow.showRow(branches,
                                                          onValueChange = {
                                                              branchName = it
                                                          },
                                                          onSelectChange = {
                                                              branchName = it
                                                          })

        Spacer(Modifier.height(20.dp))
        this.frontpageGitChipCheckoutsRow.showRow(chipsSelected,
                                                                branchName,
                                                                onBranchesUpdate = {
                                                                    flagUpdatedBranchList = true
                                                                })
    }

    @Composable
    private fun getProjectsData(chipsSelected: MutableMap<String, Boolean>) {
        if(chipsSelected.isEmpty()) {
            TheResources.getResources().projects
                .forEach { chipsSelected[it.task] = false }
        }
    }

    @Composable
    private fun updateBranchesList(branches: MutableList<String>,
                                   chipsSelected: MutableMap<String, Boolean>,
                                   onFinish: () -> Unit) {
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            val branchesList = loadBranches(chipsSelected)
            branches.clear()
            branches.addAll(branchesList)
            onFinish()
        }
    }

    private suspend fun loadBranches(chipsSelected: MutableMap<String, Boolean>): List<String> {
        val operationsUseCase: OperationsUseCase = UseCaseFactory.getOperationsUseCase()
        var result: List<String> = listOf()

        val chips = chipsSelected.filter { it -> it.value }
        if (chips.isEmpty() || chips.size > 1) {
            logger.info { "Se debe seleccionar un proyecto" }
            return result
        }

        withContext(Dispatchers.IO) {
            val defer = async(Dispatchers.IO) {
                val chipsSelectedList = chips.keys.toList()
                val res = operationsUseCase.getBranches(chipsSelectedList[0])

                return@async res.data
            }
            result = defer.await()
        }//scope

        return result
    }
}