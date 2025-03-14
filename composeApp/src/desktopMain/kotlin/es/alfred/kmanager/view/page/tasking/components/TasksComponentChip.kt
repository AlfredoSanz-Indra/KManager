package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksComponentChip {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun show(item: String, onSelectedChip: (String) -> Unit) {
        var selected by remember { mutableStateOf(false) }
        logger.info { "show -> item: $item" }

        FilterChip(
            selected = selected,
            onClick = {
                selected = !selected
                when(selected) {
                    true -> onSelectedChip(item)
                    false -> onSelectedChip("N-$item")
                }
            },
            label = { Text(item) },
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
                    contentDescription = item,
                    Modifier.size(FilterChipDefaults.IconSize)
                )
            },
        )//Chips
        Spacer(Modifier.width(20.dp))
    }
}