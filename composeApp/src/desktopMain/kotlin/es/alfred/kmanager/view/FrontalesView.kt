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

    var chipsGitSelected: MutableMap<String, Boolean> = mutableMapOf()
    var chipsNodeSelected: MutableMap<String, Boolean> = mutableMapOf()

    init {
        initGlobal()
    }

    private fun initGlobal() {
        logger.info { "initializing, loading data from resources" }
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
        logger.info { "creating view Header with menu buttons" }
        var theview: Byte by remember { mutableStateOf(0) }
        var curView:Byte by remember { mutableStateOf(theview) }

        MaterialTheme(darkColorScheme(background = Color.Black)) {
            Column {
                Row(
                    Modifier.background(color = Color(0xFFF8F7FF))
                        .height(130.dp)
                        .width(800.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    frontPageMainButtonsRow.createPage(onViewChange = {theview = it})
                }

                if(curView != theview) {
                    initGlobal()
                    curView = theview
                }

                if (Constants.theviewGit == theview) {
                    frontPageGit.createPage(chipsGitSelected)
                }

                if (Constants.theviewNode == theview) {
                    frontPageNode.createPage(chipsNodeSelected)
                }

                if (Constants.theviewTests == theview) {
                    frontPageMongo.createPage()
                }
            }
        }
    }
}