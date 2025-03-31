package es.alfred.kmanager.domain.dataapi

import es.alfred.kmanager.data.mongo.entity.ContextProject
import es.alfred.kmanager.data.mongo.results.TasksContextResult

/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface TasksContextDAO {

    suspend fun upsertTasksContextProject(contextProject: ContextProject): TasksContextResult

    suspend fun getTasksContextCurrentProject(): TasksContextResult
}