package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.text.input.getTextBeforeSelection
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksComponentText {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun show(conf: TasksComponentTextConf, onValueChange: (String) -> Unit) {
        var textValue by rememberSaveable { mutableStateOf(TextFieldValue(conf.initialText, TextRange(3, 100))) }
        var selectedText by rememberSaveable { mutableStateOf("") }
        var selectedTextBefore by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = textValue,
            modifier = Modifier
                .height(conf.heigth)
                .fillMaxWidth(conf.width)
                .padding(0.dp),
            onValueChange = {
                if (it.text.length <= 100) {
                    textValue = it
                    onValueChange(textValue.text)
                }
                selectedText = it.getSelectedText().text
                selectedTextBefore = it.getTextBeforeSelection(5000).text
            },
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
                )},
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
            trailingIcon = {
                IconButton(
                    onClick = {
                        textValue = TextFieldValue("")
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