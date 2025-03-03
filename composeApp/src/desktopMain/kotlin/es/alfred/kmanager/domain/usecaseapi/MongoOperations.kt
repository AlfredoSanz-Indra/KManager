package es.alfred.kmanager.domain.usecaseapi

import es.alfred.kmanager.domain.model.BooleanResult

/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface MongoOperations {

    suspend fun isAlive(): BooleanResult
}