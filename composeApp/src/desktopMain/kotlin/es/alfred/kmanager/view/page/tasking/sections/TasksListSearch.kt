package es.alfred.kmanager.view.page.tasking.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.alfred.kmanager.view.page.tasking.viewmodel.TasksListViewModel
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksListSearch {

    private val logger = KotlinLogging.logger {}

    @Composable
    fun showRow(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        logger.info { "showRow -> list: ${viewModel.uiState.value.taskStateList}" }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Row(
            Modifier
            .background(color = Color(0xFFf7f6ff))
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(25.dp))
            for(item in uiState.taskStateList) {
                taskSearchChip(item)
            }
        }

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            taskSearchText()

            Spacer(Modifier.width(20.dp))
            taskSearchButton()
        }

        if(uiState.generalError) {
            Row(
                Modifier
                    .background(color = Color(0xFFf7f6ff))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(25.dp))
                taskErrors()
            }
        }
    }

    @Composable
    private fun taskSearchChip(item: String,
                               viewModel: TasksListViewModel = viewModel { TasksListViewModel() },
                               ) {
        var selected by remember { mutableStateOf(false) }
        logger.info { "taskSearchChip -> item: $item" }

        FilterChip(
            selected = selected,
            onClick = {
                selected = !selected
                when(selected) {
                    true -> viewModel.addTaskStateToSearchList(item)
                    false -> viewModel.addTaskStateToSearchList("N-" + item)
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

    @Composable
    private fun taskSearchText(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        var searchFieldValue by rememberSaveable { mutableStateOf(TextFieldValue("", TextRange(3, 100))) }

        OutlinedTextField(
            value = searchFieldValue,
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth(0.75f)
                .padding(5.dp),
            onValueChange = {
                if (it.text.length <= 100) {
                    searchFieldValue = it
                    viewModel.updateTaskFieldSearch(searchFieldValue.text)
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
            label = { Text("Search text") },
            placeholder = { Text(text = "*Type the text to search") },
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
                        searchFieldValue = TextFieldValue("")
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            }
        )
    }

    @Composable
    private fun taskSearchButton(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color(0xFF666699) else Color(0xFF336699)
        val borderColor = if (isPressed) Color.Black else Color(0xFF666699)

        OutlinedButton(modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color,
                contentColor = Color(0xFFF5F5F5),
                disabledContentColor = Color(0XFFe83151),
                disabledContainerColor = Color(0XFFe83151)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(borderColor, borderColor))),
            interactionSource = interactionSource,
            onClick = {
                viewModel.search()
            }
        )
        {
            Text("Search")
        }
    }

    @Composable
    private fun taskErrors(viewModel: TasksListViewModel = viewModel { TasksListViewModel() }) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            uiState.generalErrorText, color = Color.Red, style = TextStyle(
                fontSize = 15.sp, color = Color.Red
            )
        )
    }
}