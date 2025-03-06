package es.alfred.kmanager.core.di

import es.alfred.kmanager.domain.usecase.AntUseCaseImpl
import es.alfred.kmanager.domain.usecase.OperationsUseCaseImpl
import es.alfred.kmanager.domain.usecaseapi.AntUseCase
import es.alfred.kmanager.domain.usecaseapi.OperationsUseCase

/**
 * @author Alfredo Sanz
 * @time 2025
 */
object UseCaseFactory {

    private lateinit var antUseCase: AntUseCase
    private lateinit var operationsUseCase: OperationsUseCase

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
}