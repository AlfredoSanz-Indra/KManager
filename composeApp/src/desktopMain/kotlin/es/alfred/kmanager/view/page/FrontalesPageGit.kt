package es.alfred.kmanager.view.page

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.view.page.section.*
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGit {
    private val logger = KotlinLogging.logger {}

    private val frontpageGitPullButtonsRow: FrontalesPageGitPullButtonsRow = FrontalesPageGitPullButtonsRow();
    private val frontpageGitChipRow: FrontalesPageGitChipsRow = FrontalesPageGitChipsRow()
    private val frontpageGitChipActionButtons: FrontalesPageGitChipsActionButtonsRow = FrontalesPageGitChipsActionButtonsRow()
    private val frontpageGitChipCheckoutsRow: FrontalesPageGitChipsOperationsRow = FrontalesPageGitChipsOperationsRow()
    private val frontPageGitBranchControlsRow: FrontalesPageGitBranchControlsRow = FrontalesPageGitBranchControlsRow()

    @Composable
    fun createPage( chipsSelected: MutableMap<String, Boolean>) {
        logger.info { "creating view GIT" }

        var branchName by remember  { mutableStateOf("") }
        val branches: MutableList<String> = remember { mutableStateListOf("feature/angular18", "feature/alertas") }

        Spacer(Modifier.height(20.dp))
        this.frontpageGitPullButtonsRow.gitpullsButtonRow()

        Spacer(Modifier.height(20.dp))
        this.frontpageGitChipRow.gitChipsRow(chipsSelected)

        Spacer(Modifier.height(20.dp))
        this.frontpageGitChipActionButtons.gitChipsActionsRow(chipsSelected)

        Spacer(Modifier.height(20.dp))
        this.frontPageGitBranchControlsRow.gitControlsRow(
            branches,
            onValueChange = {
                branchName = it
            },
            onSelectChange = {
                branchName = it
            }
        )

        Spacer(Modifier.height(20.dp))
        this.frontpageGitChipCheckoutsRow.gitChipsOperationsRow(chipsSelected, branchName)
    }
}