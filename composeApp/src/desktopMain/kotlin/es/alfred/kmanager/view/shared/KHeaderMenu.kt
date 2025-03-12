package es.alfred.kmanager.view.shared

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
import es.alfred.kmanager.actionFrontales
import es.alfred.kmanager.actionTasks
import es.alfred.kmanager.core.Constants
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class KHeaderMenu {
    private val logger = KotlinLogging.logger {}

    @Composable
    fun createPage(onChangeView: (String) -> Unit) {
        logger.info { "KHeaderMenu createPage" }
        MaterialTheme(darkColorScheme(background = Color.Black)) {
            Column {
                Row(
                    Modifier
                        .background(color = Color(0xFF6a5acd))
                        .height(50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(20.dp))
                    actionbutton("Frontales",
                                  onViewChoose = { onChangeView(actionFrontales) })

                    Spacer(Modifier.width(20.dp))
                    actionbutton("Tasks",
                                  onViewChoose = { onChangeView(actionTasks) })
                }
            }
        }
    }

    @Composable
    private fun actionbutton(text: String, onViewChoose: (Byte) -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF949601) else Color(0xFF18d4b7)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)

        OutlinedButton(modifier = Modifier.width(100.dp)
                                          .height(35.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color,
                contentColor = Color(0xFF4718d4),
                disabledContentColor = Color(0XFFe83151),
                disabledContainerColor = Color(0XFFe83151)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                onViewChoose(Constants.theviewGit)
            }
        ) {
            Text(text)
        }
    }
}