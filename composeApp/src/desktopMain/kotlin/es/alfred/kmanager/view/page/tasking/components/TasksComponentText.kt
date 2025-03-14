package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksComponentText {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun show(initialText: String, label: String, width: Float, onValueChange: (String) -> Unit) {
        var textValue by rememberSaveable { mutableStateOf(TextFieldValue(initialText, TextRange(3, 100))) }

        OutlinedTextField(
            value = textValue,
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth(width)
                .padding(5.dp),
            onValueChange = {
                if (it.text.length <= 100) {
                    textValue = it
                    onValueChange(textValue.text)
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                letterSpacing = 0.1.em,
                background = Color.White,
                textDecoration = TextDecoration.None
            ),
            label = { Text(label) },
            placeholder = { Text(text = "type $label") },
            isError = false,
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Gray,
                errorTextColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.Gray,
                errorContainerColor = Color.Yellow,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                focusedPlaceholderColor = Color.LightGray,
                disabledPlaceholderColor = Color.LightGray,
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        textValue = TextFieldValue("")
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            }
        )
    }
}