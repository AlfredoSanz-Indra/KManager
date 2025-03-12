package es.alfred.kmanager.view

import androidx.compose.runtime.*
import es.alfred.kmanager.view.shared.KHeaderMenu
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksView() : IView {
    private val logger = KotlinLogging.logger {}
    private val kheaderMenu: KHeaderMenu = KHeaderMenu()

    @Composable
    override fun createView(onChangeView: (String) -> Unit) {
        logger.info { "createView" }
        var showView: Byte by remember { mutableStateOf(0) }

        kheaderMenu.createPage(onChangeView)
    }
}