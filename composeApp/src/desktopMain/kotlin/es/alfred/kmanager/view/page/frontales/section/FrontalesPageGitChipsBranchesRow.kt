package es.alfred.kmanager.view.page.frontales.section

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGitChipsBranchesRow {

    @Composable
    fun showRow(branchList:List<String>,
                onValueChange: (String) -> Unit,
                onSelectChange: (String) -> Unit) {
        var option: Int by remember { mutableStateOf(1) }

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            gitRadioButton(onOptionSelect = { option = it })
        }

        Row(
            Modifier
                .background(color = Color(0xFFf7f6ff))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            when(option) {
                1 -> gitBranchText(onValueChange)
                2 -> gitBranchSelect(branchList, onSelectChange)
            }
        }
    }

    @Composable
    fun gitRadioButton(onOptionSelect: (Int) -> Unit) {
        var rSelected: Int by remember { mutableStateOf(1) }

        Row(
            Modifier.width(200.dp)
                .height(56.dp)
                .selectable(
                    selected = rSelected == 1,
                    onClick = {
                        rSelected = 1
                        onOptionSelect(1)
                    },
                    role = Role.RadioButton
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = rSelected == 1,
                onClick = {
                    rSelected = 1
                    onOptionSelect(1)
                }
            )
            Text(
                text = "New Branch",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(Modifier.width(20.dp))

        Row(
            Modifier.width(250.dp)
                .height(56.dp)
                .selectable(
                    selected = rSelected == 2,
                    onClick = {
                                rSelected = 2
                                onOptionSelect(2)
                              },
                    role = Role.RadioButton
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = rSelected == 2,
                onClick = {
                    rSelected = 2
                    onOptionSelect(2)
                }
            )
            Text(
                text = "Select Branch",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

    @Composable
    fun gitBranchText(onValueChange: (String) -> Unit) {
        var txBranchName by rememberSaveable { mutableStateOf(TextFieldValue("", TextRange(5, 70))) }

        OutlinedTextField(
            value = txBranchName,
            modifier = Modifier.height(90.dp).fillMaxSize(1f).padding(10.dp),
            onValueChange = {
                txBranchName = it
                onValueChange(txBranchName.text)
            },
            label = { Text("Branch name") },
            placeholder = { Text(text = "*Type the branch name") },
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
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun gitBranchSelect(branches: List<String>, onSelectChange: (String) -> Unit) {
        var isExpanded by remember { mutableStateOf(false) }
        var selectedBranch by remember { mutableStateOf( "" ) }

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { newValue ->
                isExpanded = newValue
            },
        ) {
            OutlinedTextField(
                value = selectedBranch,
                modifier = Modifier.height(90.dp).fillMaxSize(1f).padding(10.dp),
                onValueChange = {
                },
                label = { Text(text = "Branches") },
                placeholder = { Text("Select branch") },
                singleLine = true,
                maxLines = 1,
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
            ) {
                val listBranches: List<String> = branches
                listBranches.forEach {
                    DropdownMenuItem(
                        text = { Text(it, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            isExpanded = false
                            selectedBranch = it
                            onSelectChange(it)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}