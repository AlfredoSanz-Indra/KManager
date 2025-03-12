package es.alfred.kmanager.view.page.frontales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.view.page.frontales.section.FrontalesPageNodeChipsButtonsRow
import es.alfred.kmanager.view.page.frontales.section.FrontalesPageNodeChipsRow
import es.alfred.kmanager.view.page.frontales.section.FrontalesPageNodeUpperRow

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageNode {
    private val frontPageNodeUpperRow: FrontalesPageNodeUpperRow = FrontalesPageNodeUpperRow()
    private val frontPageNodeChipsRow: FrontalesPageNodeChipsRow = FrontalesPageNodeChipsRow()
    private val frontPageNodeChipsButtonsRow: FrontalesPageNodeChipsButtonsRow = FrontalesPageNodeChipsButtonsRow()

    @Composable
    fun createPage() {
        var flagFirstTime by remember { mutableStateOf(true) }
        val chipsSelected: MutableMap<String, Boolean> = remember { mutableStateMapOf() }

        if(flagFirstTime) {
            getProjectsData(chipsSelected)
        }
        flagFirstTime = false

        Spacer(Modifier.height(20.dp).background(color = Color(0xFFf7f6ff)).fillMaxWidth())
        frontPageNodeUpperRow.showRow()

        Spacer(Modifier.height(20.dp).background(color = Color(0xFFf7f6ff)).fillMaxWidth())
        frontPageNodeChipsRow.showRow(chipsSelected)

        Spacer(Modifier.height(20.dp).background(color = Color(0xFFf7f6ff)).fillMaxWidth())
        frontPageNodeChipsButtonsRow.showRow(chipsSelected)
    }

    @Composable
    private fun getProjectsData(chipsSelected: MutableMap<String, Boolean>) {
        if(chipsSelected.isEmpty()) {
            TheResources.getResources().projects
                .filter { it.runnable }
                .forEach { chipsSelected[it.task] = false }
        }
    }
}