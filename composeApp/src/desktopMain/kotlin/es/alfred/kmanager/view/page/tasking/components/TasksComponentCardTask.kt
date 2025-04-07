package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksComponentCardTask {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun show(onEdit: () -> Unit, onDelete: () -> Unit) {
        var selected by remember { mutableStateOf(false) }

        OutlinedCard(
            modifier = Modifier
                .padding(vertical = 3.dp)
                .fillMaxWidth()
                .height(100.dp)
                .waterfallPadding(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color(0xFF2a4481),
                contentColor = Color.Black,
                disabledContainerColor = Color.Blue,
                disabledContentColor = Color.Black
            ),
            elevation = CardDefaults.outlinedCardElevation(),
            border = BorderStroke(1.dp, Color.White),
        )
        {
            Column(
                Modifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            )
            {

                Spacer(modifier = Modifier.height(10.dp))
                rowOne("Linea 1")
                Spacer(modifier = Modifier.height(10.dp))
                rowOne("Linea 2")

            } //Column
        } //card
    }

    @Composable
    private fun rowOne(tex: String) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal= 20.dp, vertical = 2.dp),
        ) {
            Text(
                text = tex,
                style = TextStyle(
                    color = Color.White
                ),
            )
        }
    }
}