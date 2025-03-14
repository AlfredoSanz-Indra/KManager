package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TaskComponentError {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun show(errorText: String) {
        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                errorText, color = Color.Red, style = TextStyle(
                    fontSize = 15.sp, color = Color.Red
                )
            )
        }
    }
}