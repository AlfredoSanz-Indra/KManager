package es.alfred.kmanager

import androidx.compose.runtime.*
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.view.IView
import es.alfred.kmanager.view.FrontalesView
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
const val actionFrontales: String = "FRONTALES"


@Composable
private fun initGlobal() {
    val logger = KotlinLogging.logger {}
    var flagInit by remember { mutableStateOf(false) }

    logger.info { "initGlobal" }
    if(!flagInit) {
        logger.info { "initGlobal loading getResources" }
        flagInit = true
        TheResources.getResources()
    }
}

@Composable
private fun app(action: String) {
    val v: IView

    initGlobal()

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
        title = "KManager 1.0.11",
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