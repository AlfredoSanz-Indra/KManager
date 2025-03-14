package es.alfred.kmanager.view.page.frontales.section

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.Constants

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageMainButtons {

    @Composable
    fun createPage(onViewChange: (Byte) -> Unit) {

        var v: Byte by remember { mutableStateOf(0) }
        var v2: Byte by remember { mutableStateOf(v) }

        if(v != v2) {
            onViewChange(v)
            v2 = v
        }

        Spacer(Modifier.width(40.dp))
        buttonGitCommands(onViewChoose = {v = it})

        Spacer(Modifier.width(10.dp))
        buttonNodeCommands(onViewChoose = {v = it})

        Spacer(Modifier.width(10.dp))
        buttonTest(onViewChoose = {v = it})
    }

    @Composable
    private fun buttonGitCommands(onViewChoose: (Byte) -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF949601) else Color(0xFF35682d)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)

        OutlinedButton(modifier = Modifier.width(200.dp)
            .height(70.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color,
                contentColor = Color(0xFFF5F5F5),
                disabledContentColor = Color(0XFFe83151),
                disabledContainerColor = Color(0XFFe83151)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                onViewChoose(Constants.theviewGit)
            }
        ) {
            Text("Git Commands")
        }
    }

    @Composable
    private fun buttonNodeCommands(onViewChoose: (Byte) -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF949601) else Color(0xFF35682d)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)

        OutlinedButton(modifier = Modifier.width(200.dp)
            .height(70.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color,
                contentColor = Color(0xFFF5F5F5),
                disabledContentColor = Color(0XFFe83151),
                disabledContainerColor = Color(0XFFe83151)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                onViewChoose(Constants.theviewNode)
            }
        ) {
            Text("Node Commands")
        }
    }

    @Composable
    private fun buttonTest(onViewChoose: (Byte) -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF949601) else Color(0xFF35682d)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)

        OutlinedButton(modifier = Modifier.width(100.dp).height(50.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color,
                contentColor = Color(0xFFF5F5F5),
                disabledContentColor = Color(0XFFe83151),
                disabledContainerColor = Color(0XFFe83151)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                onViewChoose(Constants.theviewTests)
            }
        ) {
            Text("Mongo")
        }
    }
}