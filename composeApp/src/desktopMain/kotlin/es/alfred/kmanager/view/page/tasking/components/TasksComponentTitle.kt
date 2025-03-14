package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksComponentTitle {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun show(title: String) {
        Row(
            Modifier
                .background(color = Color(0xFFefe8ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(25.dp))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                title,
                color = Color.Black,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    letterSpacing = 0.1.em,
                    background = Color.Transparent,
                    textDecoration = TextDecoration.None
                ),
            )
        }
    }
}