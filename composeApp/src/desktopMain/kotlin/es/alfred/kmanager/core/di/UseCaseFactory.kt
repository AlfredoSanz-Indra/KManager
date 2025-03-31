package es.alfred.kmanager.core.di

import es.alfred.kmanager.domain.usecase.AntUseCaseImpl
import es.alfred.kmanager.domain.usecase.OperationsUseCaseImpl
import es.alfred.kmanager.domain.usecase.TasksContextUseCaseImpl
import es.alfred.kmanager.domain.usecaseapi.AntUseCase
import es.alfred.kmanager.domain.usecaseapi.OperationsUseCase
import es.alfred.kmanager.domain.usecaseapi.TasksContextUseCase

/**
 * @author Alfredo Sanz
 * @time 2025
 */
object UseCaseFactory {

    private lateinit var antUseCase: AntUseCase
    private lateinit var operationsUseCase: OperationsUseCase
    private lateinit var tasksContextUseCase: TasksContextUseCase

    fun getAntUseCase(): AntUseCase {
        if (!this::antUseCase.isInitialized) {
            this.antUseCase = AntUseCaseImpl()
        }
        return this.antUseCase
    }

    fun getOperationsUseCase(): OperationsUseCase {
        if (!this::operationsUseCase.isInitialized) {
            this.operationsUseCase = OperationsUseCaseImpl()
        }
        return this.operationsUseCase
    }

    fun getTasksContextUseCase(): TasksContextUseCase {
        if (!this::tasksContextUseCase.isInitialized) {
            this.tasksContextUseCase = TasksContextUseCaseImpl()
        }
        return this.tasksContextUseCase
    }
}