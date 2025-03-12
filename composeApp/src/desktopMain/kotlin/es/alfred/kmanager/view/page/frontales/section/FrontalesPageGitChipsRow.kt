package es.alfred.kmanager.view.page.frontales.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.alfred.kmanager.core.Util
import es.alfred.kmanager.core.model.Project
import es.alfred.kmanager.core.resources.TheResources


/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGitChipsRow {

    @Composable
    fun showRow(chipsSelected: MutableMap<String, Boolean>, onChipSelected: (Boolean) -> Unit) {
        val items: List<Any> = TheResources.getResources().projects
        val itemsGroup: List<List<Any>> = Util.groupItems(items, 4)

        for(group in itemsGroup) {
            Row(
                Modifier.background(color = Color.White).width(800.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(20.dp))

                for(item in group) {
                    gitChip(item as Project, chipsSelected, onChipSelected)
                }
            }//row
        }//for
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun gitChip(item: Project, chipsSelected: MutableMap<String, Boolean>, onChipSelected: (Boolean) -> Unit) {
        var selected by remember { mutableStateOf(false) }

        FilterChip(
            selected = selected,
            onClick = {
                selected = !selected
                chipsSelected[item.task] = selected
                onChipSelected(true)
            },
            label = { Text(item.label) },
            modifier = Modifier,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = Color(0xFF7BB661),
                labelColor = Color.White,
                selectedContainerColor = Color(0xFF666611),
                selectedLabelColor = Color.White
            ),
            leadingIcon = {
                Icon(
                    when (selected) {
                        true -> Icons.Filled.Check
                        false -> Icons.Filled.Add
                    },
                    contentDescription = item.name,
                    Modifier.size(FilterChipDefaults.IconSize)
                )
            },
        )//Chips
        Spacer(Modifier.width(20.dp))
    }
}