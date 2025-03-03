package es.alfred.kmanager.view.page.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class FrontalesPageGitBranchControlsRow {

    @Composable
    fun gitControlsRow(onValueChange: (String) -> Unit) {
        Row(
            Modifier.background(color = Color.White).width(800.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))
            gitControlsRowTexts(onValueChange)
        }

    }

    @Composable
    fun gitControlsRowTexts(onValueChange: (String) -> Unit) {

        var txBranchName by rememberSaveable { mutableStateOf(TextFieldValue("", TextRange(5, 70))) }

        OutlinedTextField(
            value = txBranchName,
            modifier = Modifier.fillMaxWidth(),
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
}