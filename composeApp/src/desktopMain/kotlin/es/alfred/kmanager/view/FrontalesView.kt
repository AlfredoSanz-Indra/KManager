package es.alfred.kmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.Constants
import es.alfred.kmanager.view.page.frontales.FrontalesPageGit
import es.alfred.kmanager.view.page.frontales.FrontalesPageMongos
import es.alfred.kmanager.view.page.frontales.FrontalesPageNode
import es.alfred.kmanager.view.page.frontales.section.FrontalesPageMainButtons
import es.alfred.kmanager.view.shared.KHeaderMenu
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class FrontalesView() : IView {
    private val logger = KotlinLogging.logger {}

    private val kheaderMenu: KHeaderMenu = KHeaderMenu()
    private val frontPageMainButtonsRow: FrontalesPageMainButtons = FrontalesPageMainButtons()
    private val frontPageGit: FrontalesPageGit = FrontalesPageGit()
    private val frontPageNode: FrontalesPageNode = FrontalesPageNode()
    private val frontPageMongo: FrontalesPageMongos = FrontalesPageMongos()

    @Composable
    override fun createView(onChangeView: (String) -> Unit) {
        logger.info { "createView" }
        var showView: Byte by remember { mutableStateOf(0) }

        MaterialTheme(darkColorScheme(background = Color.Black)) {
            Column {
                kheaderMenu.createPage(onChangeView)
                Spacer(Modifier.height(1.dp))

                Row(
                    Modifier
                        .background(color = Color(0xFFefe8ff))
                        .height(100.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    frontPageMainButtonsRow.createPage(onViewChange = {showView = it})
                }

                if (Constants.theviewGit == showView) {
                    frontPageGit.createPage()
                }

                if (Constants.theviewNode == showView) {
                    frontPageNode.createPage()
                }

                if (Constants.theviewTests == showView) {
                    frontPageMongo.createPage()
                }
            }
        }
    }
}