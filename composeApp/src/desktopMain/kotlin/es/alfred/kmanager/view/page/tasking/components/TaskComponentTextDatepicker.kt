package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TaskComponentTextDatepicker {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun show(conf: TasksComponentTextConf, onOpenDatepicker: () -> Unit, onDateDeleted: () -> Unit) {
        val selectedText by rememberSaveable { mutableStateOf("") }
        val selectedTextBefore by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = conf.initialText,
            modifier = Modifier
                .height(conf.heigth)
                .fillMaxWidth(conf.width)
                .padding(0.dp),
            onValueChange = { },
            readOnly = true,
            textStyle = TextStyle(
                color = Color(0xFF164462),
                fontSize = conf.fontSize,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W500,
                fontStyle = FontStyle.Normal,
                letterSpacing = 0.em,
                background = Color.White,
                textDecoration = TextDecoration.None
            ),
            label = { Text(conf.label) },
            placeholder = {
                Text(
                    text = "type ${conf.label}",
                    style = TextStyle(
                        fontSize = conf.fontSize,
                        color = Color.LightGray
                    ),
                )
            },
            isError = false,
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Blue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = Color.Black,
                focusedPlaceholderColor = Color.LightGray,
                selectionColors = TextSelectionColors(Color.Blue, backgroundColor = Color.Yellow)
            ),
            leadingIcon = {
                IconButton(
                    onClick = {
                        onOpenDatepicker()
                    }
                ) {
                    Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = null)
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onDateDeleted()
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            },
            visualTransformation = if(selectedText.isNotEmpty()) {
                TasksComponentTextVisualTransfHighlighting(selectedText, selectedTextBefore)
            }
            else {
                VisualTransformation.None
            }

        )
    }
}