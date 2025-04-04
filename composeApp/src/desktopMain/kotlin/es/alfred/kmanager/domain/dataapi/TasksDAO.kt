package es.alfred.kmanager.domain.dataapi

import es.alfred.kmanager.data.mongo.entity.MngTask
import es.alfred.kmanager.data.mongo.results.TasksResult

/**
 * @author Alfredo Sanz
 * @date 2025
 */
interface TasksDAO {

    suspend fun insertTask(task: MngTask): TasksResult
}