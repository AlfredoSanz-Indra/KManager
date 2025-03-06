package es.alfred.kmanager.domain.usecaseapi

import es.alfred.kmanager.domain.model.BooleanResult
import es.alfred.kmanager.domain.model.ListResult

/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface OperationsUseCase {

    suspend fun isAlive(): BooleanResult

    suspend fun addBranch(project: String, branchName: String): ListResult

    suspend fun getBranches(project: String): ListResult
}