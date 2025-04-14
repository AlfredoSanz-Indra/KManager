package es.alfred.kmanager.domain.dataapi

import es.alfred.kmanager.data.mongo.entity.MngTask
import es.alfred.kmanager.data.mongo.model.MgFilterTask
import es.alfred.kmanager.data.mongo.results.SimpleResult
import es.alfred.kmanager.data.mongo.results.TasksListResult
import es.alfred.kmanager.data.mongo.results.TasksResult

/**
 * @author Alfredo Sanz
 * @date 2025
 */
interface TasksDAO {

    suspend fun insertTask(task: MngTask): TasksResult

    suspend fun getTasks(filter: MgFilterTask): TasksListResult

    suspend fun deleteTask(taskID: String): SimpleResult
}