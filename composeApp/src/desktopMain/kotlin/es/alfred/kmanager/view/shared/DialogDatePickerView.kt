package es.alfred.kmanager.view.shared

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

/**
 * @author Alfredo Sanz
 * @time 2024
 */
object DialogDatePickerView {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun show(initialDate: Long, temporalDate: Long, onClose: () -> Unit, onDateSelected: (Long) -> Unit) {
        val calSelectDate = if(initialDate == 0L) {
            temporalDate
        }
        else {
            initialDate
        }
        val internalState = rememberDatePickerState(initialSelectedDateMillis = calSelectDate)

        DatePickerDialog(
            onDismissRequest = {
                onClose()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateSelected(internalState.selectedDateMillis ?: 0L)
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