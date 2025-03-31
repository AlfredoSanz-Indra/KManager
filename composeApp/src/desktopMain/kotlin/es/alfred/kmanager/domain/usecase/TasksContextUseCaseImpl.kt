package es.alfred.kmanager.domain.usecase

import es.alfred.kmanager.core.di.DataFactory
import es.alfred.kmanager.data.mongo.entity.Context
import es.alfred.kmanager.data.mongo.entity.ContextProject
import es.alfred.kmanager.domain.usecaseapi.TasksContextUseCase
import es.alfred.kmanager.domain.model.SelectData
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @date 2025
 */
class TasksContextUseCaseImpl: TasksContextUseCase {

    private val logger = KotlinLogging.logger {}
    private val tasksContextDAO = DataFactory.getTasksContextDAO()

    override suspend fun getCurrentProject(): SelectData {
        var result = SelectData("", "")

        val resultDAO = tasksContextDAO.getTasksContextCurrentProject()
        if(resultDAO.result) {
            val context = resultDAO.data["context"] as Context
            result = SelectData(context.project!!.name, context.project!!.label)
        }
        logger.info { "getCurrentProject -> result: $result" }
        return result
    }

    override suspend fun insertUpdateCurrentProject(project: SelectData): SelectData {
        var result = SelectData("", "")

        val contextProject = ContextProject(project.name, project.label, "current")
        val entity = Context(null, contextProject)
        val resultDAO = tasksContextDAO.upsertTasksContextProject(entity)
        if(resultDAO.result) {
            val context = resultDAO.data["context"] as Context
            result = SelectData(context.project!!.name, context.project!!.label)
        }
        logger.info { "insertUpdateCurrentProject -> result: $result" }
        return result
    }
}