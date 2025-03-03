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
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.view.page.FrontalesPageGit
import es.alfred.kmanager.view.page.FrontalesPageMongo
import es.alfred.kmanager.view.page.FrontalesPageNode
import es.alfred.kmanager.view.page.section.FrontalesPageMainButtons
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class FrontalesView() : IView {
    private val logger = KotlinLogging.logger {}

    private val frontPageMainButtonsRow: FrontalesPageMainButtons = FrontalesPageMainButtons()
    private val frontPageGit: FrontalesPageGit = FrontalesPageGit()
    private val frontPageNode: FrontalesPageNode = FrontalesPageNode()
    private val frontPageMongo: FrontalesPageMongo = FrontalesPageMongo()

    @Composable
    private fun initGlobal(chipsGitSelected: MutableMap<String, Boolean>,
                           chipsNodeSelected: MutableMap<String, Boolean>) {
        logger.info { "initGlobal, loading data from resources" }
        if(chipsGitSelected.isEmpty()) {
            logger.info { "loading chipsGitSelected" }
            TheResources.getProjects().projects
                .forEach { it -> chipsGitSelected[it.task] = false }
        }

        if(chipsNodeSelected.isEmpty()) {
            logger.info { "loading chipsNodeSelected" }
            TheResources.getProjects().projects
                .filter { it -> it.runnable }
                .forEach { it -> chipsNodeSelected[it.task] = false }
        }
    }

    @Composable
    override fun createView() {
        logger.info { "creating Frontales View" }

        var showview: Byte by remember { mutableStateOf(0) }
        val chipsGitSelected: MutableMap<String, Boolean> = remember { mutableStateMapOf() }
        val chipsNodeSelected: MutableMap<String, Boolean> = remember { mutableStateMapOf() }

        this.initGlobal(chipsGitSelected, chipsNodeSelected)

        MaterialTheme(darkColorScheme(background = Color.Black)) {
            Column {
                Row(
                    Modifier.background(color = Color(0xFFF8F7FF))
                        .height(130.dp)
                        .width(800.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    frontPageMainButtonsRow.createPage(onViewChange = {showview = it})
                }

                if (Constants.theviewGit == showview) {
                    frontPageGit.createPage(chipsGitSelected)
                }

                if (Constants.theviewNode == showview) {
                    frontPageNode.createPage(chipsNodeSelected)
                }

                if (Constants.theviewTests == showview) {
                    frontPageMongo.createPage()
                }
            }
        }
    }
}