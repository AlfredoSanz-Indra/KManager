package es.alfred.kmanager.core.di

import es.alfred.kmanager.domain.usecase.AntUseCaseImpl
import es.alfred.kmanager.domain.usecase.MongoOperationsImpl
import es.alfred.kmanager.domain.usecaseapi.AntUseCase
import es.alfred.kmanager.domain.usecaseapi.MongoOperations

/**
 * @author Alfredo Sanz
 * @time 2025
 */
object UseCaseFactory {

    private lateinit var antUseCase: AntUseCase
    private lateinit var mongoOperations: MongoOperations

    fun getAntUseCase(): AntUseCase {
        if (!this::antUseCase.isInitialized) {
            this.antUseCase = AntUseCaseImpl()
        }
        return this.antUseCase
    }

    fun getMongoOperations(): MongoOperations {
        if (!this::mongoOperations.isInitialized) {
            this.mongoOperations = MongoOperationsImpl()
        }
        return this.mongoOperations
    }
}