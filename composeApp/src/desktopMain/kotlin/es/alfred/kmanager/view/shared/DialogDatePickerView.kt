package es.alfred.kmanager.view.shared

import androidx.compose.material3.*
import androidx.compose.runtime.*
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2024
 */
object DialogDatePickerView {

    private val logger = KotlinLogging.logger {}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun show(initialDate: Long, onClose: () -> Unit, onDateSelected: (Long) -> Unit) {
        var selectedDate by remember { mutableStateOf(initialDate) }
        val internalState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)

        DatePickerDialog(
            onDismissRequest = {
                onClose()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDate = internalState.selectedDateMillis ?: 0L
                        onDateSelected(internalState.selectedDateMillis ?: 1)
                    },
                    enabled = internalState.selectedDateMillis != null
                ) {
                    Text("Accept")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text("Cancel")
                }
            },
            content = { DatePicker(state = internalState) },
        )
    }
}