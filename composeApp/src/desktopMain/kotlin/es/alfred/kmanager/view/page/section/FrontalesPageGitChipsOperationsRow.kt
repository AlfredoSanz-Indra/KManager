package es.alfred.kmanager.view.page.section

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.di.UseCaseFactory
import es.alfred.kmanager.domain.usecaseapi.AntUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGitChipsOperationsRow {

    private val antUseCase: AntUseCase = UseCaseFactory.getAntUseCase()

    @Composable
    fun gitChipsOperationsRow(chipsSelected: MutableMap<String, Boolean>, branchName: String) {
        Row(
            Modifier.background(color = Color.White).width(800.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            gitOperationsActionButtons(chipsSelected, branchName)
        }

    }

    @Composable
    fun gitOperationsActionButtons(chipsSelected: MutableMap<String, Boolean>, branchName: String) {
        var lastText = ""
        if(branchName.isNotBlank() && branchName.length > 10) {
            lastText = branchName.trim().substring (branchName.length-9)
        }
        
        gitOperationsActionCheckoutButton(chipsSelected, branchName, lastText)
        Spacer(Modifier.width(20.dp))
        
        gitOperationsActionPushButton(chipsSelected, branchName, lastText)
    }

    @Composable
    fun gitOperationsActionCheckoutButton(chipsSelected: MutableMap<String, Boolean>, branchName: String, lastText: String = "") {
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
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                val chips = chipsSelected.filter { it -> it.value }
                val canGoon = validateOperation(chips, branchName)

                if(canGoon) {
                    coroutineScope.launch {
                        val defer = async(Dispatchers.IO) {
                            val chipsSelectedList = chips.keys.toList()
                            antUseCase.gitCheckout(chipsSelectedList, branchName)
                        }
                        defer.await()
                    }
                }
            }
        )
        {
            Text("Git Checkout ...$lastText")
        }
    }

    @Composable
    fun gitOperationsActionPushButton(chipsSelected: MutableMap<String, Boolean>, branchName: String, lastText: String = "") {
        val coroutineScope = rememberCoroutineScope()
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
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                val chips = chipsSelected.filter { it -> it.value }
                val canGoon = validateOperation(chips, branchName)

                if(canGoon) {
                    coroutineScope.launch {
                        val defer = async(Dispatchers.IO) {
                            antUseCase.gitPush(branchName)
                        }
                        defer.await()
                    }
                }
            }
        )
        {
            Text("Git Push ...$lastText")
        }
    }

    private fun validateOperation(chips: Map<String, Boolean>, branchName: String): Boolean {
        var result = true
        if (chips.isEmpty()) {
            println("No hay proyecto seleccionado")
            result = false
        }
        if (chips.size > 1) {
            println("Solo se puede seleccionar un proyecto para esta funcion")
            result = false
        }
        if (branchName.isBlank() || branchName.length < 10) {
            println("Debe especificar un nombre de rama")
            result = false
        }
        return result
    }
}