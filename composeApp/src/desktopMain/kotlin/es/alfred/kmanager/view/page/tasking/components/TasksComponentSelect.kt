package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import es.alfred.kmanager.view.shared.SelectData
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksComponentSelect {

    private val logger = KotlinLogging.logger {}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun show(conf: TasksComponentSelectConf, onSelectChange: (SelectData) -> Unit) {
        var isExpanded by remember { mutableStateOf(false) }
        var selectedElement by remember { mutableStateOf( conf.defaultValue ) }

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { newValue ->
                isExpanded = newValue
            },
        ) {
            OutlinedTextField(
                value = selectedElement.label,
                modifier = Modifier
                                .height(conf.heigth)
                                .fillMaxWidth()
                                .padding(0.dp),
                onValueChange = {
                },
                label = {
                    Text(text = conf.label,
                         style = TextStyle(fontSize = conf.fontSize,
                                           color = Color.LightGray)
                    )},
                placeholder = {
                    Text(
                        text = conf.initialText,
                        style = TextStyle(fontSize = conf.fontSize,
                                          color = Color.LightGray
                        ),
                    )},
                singleLine = true,
                maxLines = 1,
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
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isExpanded,
                        modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable),
                    )
                },
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    isExpanded = !isExpanded
                                }
                            }
                        }
                    }
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                containerColor = Color(0xFF41607a),
                tonalElevation = 10.dp
            ) {
                val listElements: List<SelectData> = conf.selectList
                listElements.forEach {
                    DropdownMenuItem(
                        text = { Text(it.label,
                                      style = TextStyle(fontSize = conf.fontSize,
                                                        color = Color.White))
                               },
                        onClick = {
                            isExpanded = false
                            selectedElement = it
                            onSelectChange(it)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}