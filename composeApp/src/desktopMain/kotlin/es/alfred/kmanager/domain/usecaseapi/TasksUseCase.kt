package es.alfred.kmanager.domain.usecaseapi

import es.alfred.kmanager.domain.model.Task
import es.alfred.kmanager.domain.model.TaskResult

/**
 * @author Alfredo Sanz
 * @date 2025
 */
interface TasksUseCase {

    suspend fun saveTask(task: Task): TaskResult
}