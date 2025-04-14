package es.alfred.kmanager.domain.usecaseapi

import es.alfred.kmanager.domain.model.*

/**
 * @author Alfredo Sanz
 * @date 2025
 */
interface TasksUseCase {

    suspend fun saveTask(task: Task): TaskResult

    suspend fun getTasks(filterTasks: FilterTasks): TasksResult

    suspend fun deleteTask(id: String): BooleanResult
}