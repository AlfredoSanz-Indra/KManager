package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class TasksComponentTextConf(var initialText: String,
                                  var label: String,
                                  var width: Float,
                                  var heigth: Dp,
                                  var fontSize: TextUnit,
                                  var readOnly: Boolean,
                                  var maxChar: Int
                                 )
