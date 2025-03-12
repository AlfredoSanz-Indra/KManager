package es.alfred.kmanager.view.page.section

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.di.UseCaseFactory
import es.alfred.kmanager.domain.usecaseapi.AntUseCase
import es.alfred.kmanager.domain.usecaseapi.OperationsUseCase
import es.alfred.kmanager.view.shared.KManagerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGitChipsOperationsRow {
    private val logger = KotlinLogging.logger {}

    @Composable
    fun gitChipsOperationsRow(chipsSelected: MutableMap<String, Boolean>,
                              branchName: String,
                              onBranchesUpdate: (Boolean) -> Unit) {

        var flagOpenDialogConfirm: Boolean by remember { mutableStateOf(false) }
        var flagDoPush: Boolean by remember { mutableStateOf(false)}

        if(flagOpenDialogConfirm) {
            KManagerDialog.confirmDialog("Proceed with Push?",
                                         flagOpenDialogConfirm,
                                         onPush = {
                                             flagOpenDialogConfirm = false
                                             flagDoPush = true
                                         },
                                         onDismissPush = {
                                            flagOpenDialogConfirm = false
                                         })
        }
        if(flagDoPush) {
            flagDoPush = false
            this.makePush(chipsSelected, branchName, onBranchesUpdate)
        }

        gitChipsOperationsRowMain(chipsSelected,
                                  branchName,
                                  onBranchesUpdate,
                                  onOpenDialog= {
                                    flagOpenDialogConfirm = true
                                  })
    }

    @Composable
    private fun gitChipsOperationsRowMain(chipsSelected: MutableMap<String, Boolean>,
                                          branchName: String,
                                          onBranchesUpdate: (Boolean) -> Unit,
                                          onOpenDialog: () -> Unit) {
        Row(
            Modifier.background(color = Color.White).width(800.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            gitOperationsActionButtons(chipsSelected,
                                       branchName,
                                       onBranchesUpdate,
                                       onOpenDialog
            )
        }
    }

    @Composable
    private fun gitOperationsActionButtons(chipsSelected: MutableMap<String, Boolean>,
                                   branchName: String,
                                   onBranchesUpdate: (Boolean) -> Unit,
                                   onOpenDialog: () -> Unit) {
        var lastText = ""
        if(branchName.isNotBlank() && branchName.length > 10) {
            lastText = branchName.trim().substring (branchName.length-9)
        }
        
        gitOperationsActionCheckoutButton(chipsSelected, branchName, lastText, onBranchesUpdate)
        Spacer(Modifier.width(20.dp))
        gitOperationsActionPushButton(chipsSelected, branchName, lastText, onOpenDialog)
    }

    @Composable
    private fun gitOperationsActionCheckoutButton(chipsSelected: MutableMap<String, Boolean>,
                                          branchName: String,
                                          lastText: String = "",
                                          onBranchesUpdate: (Boolean) -> Unit) {
        val operationsUseCase: OperationsUseCase = UseCaseFactory.getOperationsUseCase()
        val antUseCase: AntUseCase = UseCaseFactory.getAntUseCase()
        val coroutineScope = rememberCoroutineScope()

        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF666699) else Color(0xFF336699)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)

        OutlinedButton(
            modifier = Modifier.width(250.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color,
                contentColor = Color(0xFFF5F5F5),
                disabledContentColor = Color(0XFFe83151),
                disabledContainerColor = Color(0XFFe83151)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                val chips = chipsSelected.filter { it -> it.value }
                val canGoon = validateOperation(chips, branchName)

                if(canGoon) {
                    coroutineScope.launch {
                        val defer = async(Dispatchers.IO) {
                            val chipsSelectedList = chips.keys.toList()
                            antUseCase.gitCheckout(chipsSelectedList[0], branchName)

                            operationsUseCase.addBranch(chipsSelectedList[0], branchName)
                        }
                        defer.await()
                        onBranchesUpdate(true)
                    }
                }
            }
        )
        {
            Text("Git Checkout ...$lastText")
        }
    }

    @Composable
    private fun gitOperationsActionPushButton(chipsSelected: MutableMap<String, Boolean>,
                                      branchName: String,
                                      lastText: String = "",
                                      onOpenDialog: () -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF666699) else Color(0xFF336699)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)
        
        OutlinedButton(
            modifier = Modifier.width(220.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color,
                contentColor = Color(0xFFF5F5F5),
                disabledContentColor = Color(0XFFe83151),
                disabledContainerColor = Color(0XFFe83151)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                val chips = chipsSelected.filter { it -> it.value }
                val canGoon = validateOperation(chips, branchName)

                if(canGoon) {
                    onOpenDialog()
                }
            }
        )
        {
            Text("Git Push ...$lastText")
        }
    }

    @Composable
    private fun makePush(chipsSelected: MutableMap<String, Boolean>,
                         branchName: String,
                         onBranchesUpdate: (Boolean) -> Unit) {
        val coroutineScope = rememberCoroutineScope()
        val operationsUseCase: OperationsUseCase = UseCaseFactory.getOperationsUseCase()
        val antUseCase: AntUseCase = UseCaseFactory.getAntUseCase()

        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                val chips = chipsSelected.filter { it -> it.value }
                val chipsSelectedList = chips.keys.toList()

                antUseCase.gitPush(chipsSelectedList[0], branchName)
                operationsUseCase.addBranch(chipsSelectedList[0], branchName)
            }
            defer.await()
            onBranchesUpdate(true)
        }
    }

    private fun validateOperation(chips: Map<String, Boolean>, branchName: String): Boolean {
        var result = true
        if (chips.isEmpty()) {
            logger.info { "No hay proyecto seleccionado" }
            result = false
        }
        if (chips.size > 1) {
            logger.info { "Solo se puede seleccionar un proyecto para esta funcion" }
            result = false
        }
        if (branchName.isBlank() || branchName.length < 10) {
            logger.info { "Debe especificar un nombre de rama" }
            result = false
        }
        return result
    }
}