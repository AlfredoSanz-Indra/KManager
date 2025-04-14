package es.alfred.kmanager.domain.usecase

import es.alfred.kmanager.core.di.DataFactory
import es.alfred.kmanager.data.mongo.entity.MngTask
import es.alfred.kmanager.data.mongo.model.MgFilterTask
import es.alfred.kmanager.domain.model.*
import es.alfred.kmanager.domain.usecaseapi.TasksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksUseCaseImpl: TasksUseCase {
    private val logger = KotlinLogging.logger {}

    override suspend fun deleteTask(id: String): BooleanResult {
        logger.info { "deleteTask -> id: $id" }
        var result = BooleanResult(false)

        if(!id.isNullOrBlank()) {
            val defer = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
                return@async DataFactory.getTasksDAO().deleteTask(id)
            }
            val resp = defer.await()
            if(resp.result) {
                result = BooleanResult(true)
            }
        }
        return result
    }

    override suspend fun saveTask(task: Task): TaskResult {
        logger.info { "saveTask -> task: $task" }
        val result = if(task.id.isNullOrBlank()) {
                        saveNewTask(task)
                     }
                     else {
                        saveExistingTask(task)
                     }

        return result
    }

    private suspend fun saveNewTask(task: Task): TaskResult {
        logger.info { "saveNewTask" }
        var result = TaskResult(false, null)

        val entity = mapToEntity(task)
        val defer = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
            return@async DataFactory.getTasksDAO().insertTask(entity)
        }
        val resp = defer.await()
        if(resp.result) {
            val newtask = mapToModel(resp.data!!)
            result = TaskResult(true, newtask)
        }
        return result
    }

    private suspend fun saveExistingTask(task: Task): TaskResult {
        return TaskResult(false, null)
    }

    override suspend fun getTasks(filterTasks: FilterTasks): TasksResult {
        logger.info { "getTasks -> filterTasks: $filterTasks" }
        val result = TasksResult(true, listOf())
        val filter = mapToFilter(filterTasks)

        val defer = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
            return@async DataFactory.getTasksDAO().getTasks(filter)
        }
        val resp = defer.await()
        result.result = resp.result
        if(resp.result) {
            val resultList = resp.data.map { mapToModel(it)}
            result.tasks = resultList
        }
        return result
    }

    private fun mapToEntity(task: Task): MngTask {
        val result = MngTask(
            null,
            task.project,
            task.states,
            task.name,
            task.jira,
            task.description,
            task.notes,
            task.branches,
            task.commits,
            task.dateReq,
            task.dateEnd,
            task.yearReq,
            task.monthReq,
            task.dayReq,
            task.yearEnd,
            task.monthEnd,
            task.dayEnd,
            task.creationDate
        )

        return result
    }

    private fun mapToModel(entity: MngTask): Task {
        val result = Task(
            entity._id.toString(),
            entity.project,
            entity.states,
            entity.name,
            entity.jira,
            entity.description,
            entity.notes,
            entity.branches,
            entity.commits,
            entity.dateReq,
            entity.dateEnd,
            entity.yearReq,
            entity.monthReq,
            entity.dayReq,
            entity.yearEnd,
            entity.monthEnd,
            entity.dayEnd,
            entity.creationDate
        )

        return result
    }

    private fun mapToFilter(filterTask: FilterTasks): MgFilterTask {
        val filter = MgFilterTask(
            project = filterTask.project,
            states = filterTask.states,
            name = filterTask.searchField,
            description = filterTask.searchField,
            notes = filterTask.searchField
        )

        return filter
    }
}