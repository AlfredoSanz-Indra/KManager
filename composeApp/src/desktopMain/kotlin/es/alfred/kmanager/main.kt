package es.alfred.kmanager

import androidx.compose.runtime.*
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import es.alfred.kmanager.view.IView
import es.alfred.kmanager.view.FrontalesView


const val actionFrontales: String = "FRONTALES"


/**
 * @author Alfredo Sanz
 * @date 2025
 */
@Composable
private fun app(action: String) {

    var v: IView

    when(action) {
        actionFrontales -> {
            v = FrontalesView()
            v.createView()
        }
    }
}

fun main() = application {
    var action by remember { mutableStateOf(actionFrontales) }

    Window(onCloseRequest = ::exitApplication,
        title = "KManager 1.0.6",
        state = rememberWindowState(width = 850.dp, height = 710.dp)
    ) {
        MenuBar {
            Menu("Options list", mnemonic = 'F') {
                Item("Frontales", onClick = { action = actionFrontales }, shortcut = KeyShortcut(Key.C, ctrl = true))
            }
        }
        app(action)
    }
}