package es.alfred.kmanager.view.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object KManagerDialog {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun confirmDialog(theText: String, openDialog: Boolean, onAccept: () -> Unit, onDecline: () -> Unit) {
        if (openDialog) {
            BasicAlertDialog(
                onDismissRequest = {
                    onDecline()
                }
            ) {
                Surface(
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = theText
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(Modifier.wrapContentWidth()) {
                            Spacer(modifier = Modifier.width(50.dp))

                            TextButton(
                                onClick = { onDecline() },
                            ) {
                                Text("NO")
                            }

                            TextButton(
                                onClick = { onAccept() },
                            ) {
                                Text("YES")
                            }
                        }
                    }
                }
            }
        }
    }
}