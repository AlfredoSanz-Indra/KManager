package es.alfred.kmanager.view.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.alfred.kmanager.domain.model.SelectData
import es.alfred.kmanager.view.page.tasking.components.TasksComponentBigTextConf
import es.alfred.kmanager.view.page.tasking.components.TasksComponentSelectConf
import es.alfred.kmanager.view.page.tasking.components.TasksComponentTextConf

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object ComponentsConfDataFactory {

    @Composable
    fun standardTextConf(initialText: String, label: String): TasksComponentTextConf {
        return TasksComponentTextConf(initialText, label,0.9f, 65.dp, 14.sp,false, 100)
    }

    @Composable
    fun standardBigTextConf(initialText: String, label: String): TasksComponentBigTextConf {
        return TasksComponentBigTextConf(initialText, label,0.9f, 65.dp, 14.sp,false, 1000, 5, 3)
    }

    @Composable
    fun standardSelectConf(initialText: String, label: String, selectList: List<SelectData>): TasksComponentSelectConf {
        return TasksComponentSelectConf(selectList,initialText, label, 400.dp, 65.dp, 14.sp, SelectData("",""))
    }
}