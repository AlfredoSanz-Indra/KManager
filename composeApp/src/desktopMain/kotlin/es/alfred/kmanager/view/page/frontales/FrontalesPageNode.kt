package es.alfred.kmanager.view.page.frontales

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.resources.TheResources
import es.alfred.kmanager.view.page.frontales.section.FrontalesPageNodeChipsButtonsRow
import es.alfred.kmanager.view.page.frontales.section.FrontalesPageNodeChipsRow
import es.alfred.kmanager.view.page.frontales.section.FrontalesPageNodeRun01Row

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageNode {
    private val frontNodepageRow01: FrontalesPageNodeRun01Row = FrontalesPageNodeRun01Row()
    private val frontNodepageChipsRow: FrontalesPageNodeChipsRow = FrontalesPageNodeChipsRow()
    private val frontNodepageChiposButtonsRow: FrontalesPageNodeChipsButtonsRow = FrontalesPageNodeChipsButtonsRow()

    @Composable
    fun createPage() {
        var flagFirstTime by remember { mutableStateOf(true) }
        val chipsSelected: MutableMap<String, Boolean> = remember { mutableStateMapOf() }

        if(flagFirstTime) {
            getProjectsData(chipsSelected)
        }
        flagFirstTime = false

        Spacer(Modifier.height(20.dp))
        frontNodepageRow01.getNodeRunRow01()

        Spacer(Modifier.height(20.dp))
        frontNodepageChipsRow.nodeChipsRow(chipsSelected)

        Spacer(Modifier.height(40.dp))
        frontNodepageChiposButtonsRow.nodeChipsActionsRow(chipsSelected)
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