package es.alfred.kmanager.view.page.frontales.section

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
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
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGitChipsButtonsRow {
    private val logger = KotlinLogging.logger {}
    private val antUseCase: AntUseCase = UseCaseFactory.getAntUseCase()

    @Composable
    fun showRow(chipsSelected: MutableMap<String, Boolean>) {

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            gitPullButton(chipsSelected)
        }
    }

    @Composable
    private fun gitPullButton(chipsSelected: MutableMap<String, Boolean>) {
        val coroutineScope = rememberCoroutineScope()
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF666699) else Color(0xFF336699)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)

        OutlinedButton(modifier = Modifier.width(200.dp),
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
                val canGoon = validateOperation(chips)

                if(canGoon) {
                    coroutineScope.launch {
                        val defer = async(Dispatchers.IO) {
                            val chipsSelectedList = chips.keys.toList()
                            antUseCase.gitPullList(chipsSelectedList)
                        }
                        defer.await()
                    }
                }
            }
        )
        {
            Text("Git pull projects")
        }
    }

    private fun validateOperation(chips: Map<String, Boolean>): Boolean {
        var result = true
        if (chips.isEmpty()) {
            logger.info { "No hay proyecto seleccionado" }
            result = false
        }

        return result
    }
}