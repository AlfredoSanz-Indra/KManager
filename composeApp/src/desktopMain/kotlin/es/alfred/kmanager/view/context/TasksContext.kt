package es.alfred.kmanager.view.context

import es.alfred.kmanager.core.di.UseCaseFactory
import es.alfred.kmanager.domain.model.SelectData
import kotlinx.coroutines.*

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksContext {

    private lateinit var currentProject: SelectData

    suspend fun getCurrentProject(): SelectData {
        if(!this::currentProject.isInitialized) {
            val tasksContextUseCase = UseCaseFactory.getTasksContextUseCase()

            val defer = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
                return@async tasksContextUseCase.getCurrentProject()
            }
            currentProject = defer.await()
        }
        return currentProject
    }

    suspend fun changeCurrentProject(project: SelectData): SelectData {
        val tasksContextUseCase = UseCaseFactory.getTasksContextUseCase()

        val defer = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
            return@async tasksContextUseCase.insertUpdateCurrentProject(project)
        }
        currentProject = defer.await()

        return currentProject
    }
}