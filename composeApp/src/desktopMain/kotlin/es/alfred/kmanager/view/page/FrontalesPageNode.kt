package es.alfred.kmanager.view.page

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.view.page.section.FrontalesPageNodeChipsButtonsRow
import es.alfred.kmanager.view.page.section.FrontalesPageNodeChipsRow
import es.alfred.kmanager.view.page.section.FrontalesPageNodeRun01Row
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageNode {
    private val logger = KotlinLogging.logger {}

    private val frontNodepageRow01: FrontalesPageNodeRun01Row = FrontalesPageNodeRun01Row()
    private val frontNodepageChipsRow: FrontalesPageNodeChipsRow = FrontalesPageNodeChipsRow()
    private val frontNodepageChiposButtonsRow: FrontalesPageNodeChipsButtonsRow = FrontalesPageNodeChipsButtonsRow()

    @Composable
    fun createPage(chipsSelected: MutableMap<String, Boolean>) {
        logger.info { "creating view NODE" }

        Spacer(Modifier.height(20.dp))
        frontNodepageRow01.getNodeRunRow01()

        Spacer(Modifier.height(20.dp))
        frontNodepageChipsRow.nodeChipsRow(chipsSelected)

        Spacer(Modifier.height(40.dp))
        frontNodepageChiposButtonsRow.nodeChipsActionsRow(chipsSelected)
    }
}