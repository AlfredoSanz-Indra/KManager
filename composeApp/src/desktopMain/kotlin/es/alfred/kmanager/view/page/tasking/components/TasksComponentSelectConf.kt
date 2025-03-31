package es.alfred.kmanager.view.page.tasking.components

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import es.alfred.kmanager.domain.model.SelectData

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class TasksComponentSelectConf(val selectList: List<SelectData>,
                                    var initialText: String,
                                    var label: String,
                                    var width: Dp,
                                    var heigth: Dp,
                                    var fontSize: TextUnit,
                                    var defaultValue: SelectData
)
