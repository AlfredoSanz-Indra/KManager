package es.alfred.kmanager.view.state

import es.alfred.kmanager.core.di.UseCaseFactory
import es.alfred.kmanager.view.shared.SelectData
import kotlinx.coroutines.*
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
object TasksContext {

    private val logger = KotlinLogging.logger {}
    private lateinit var currentProject: SelectData

    suspend fun getCurrentProject(): SelectData {
        if(!this::currentProject.isInitialized) {
            val tasksContextUseCase = UseCaseFactory.getTasksContextUseCase()

            val defer = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
                logger.info { "getCurrentProject -> defer AA" }
                return@async tasksContextUseCase.getCurrentProject()
            }
            logger.info { "getCurrentProject -> defer BB" }
            currentProject = defer.await()
            logger.info { "getCurrentProject -> defer BB2, currentProject: $currentProject" }
        }
        logger.info { "getCurrentProject -> defer CC" }
        return currentProject
    }

    suspend fun changeCurrentProject(project: SelectData): SelectData {
        val tasksContextUseCase = UseCaseFactory.getTasksContextUseCase()

        val defer = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
            logger.info { "changeCurrentProject -> defer AA" }
            return@async tasksContextUseCase.insertUpdateCurrentProject(project)
        }
        logger.info { "changeCurrentProject -> defer BB" }
        currentProject = defer.await()

        return currentProject
    }
}