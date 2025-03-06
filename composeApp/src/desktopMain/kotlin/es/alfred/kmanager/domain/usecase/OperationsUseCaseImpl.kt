package es.alfred.kmanager.domain.usecase

import es.alfred.kmanager.core.di.DataFactory
import es.alfred.kmanager.data.mongo.entity.ServerAlive
import es.alfred.kmanager.domain.model.BooleanResult
import es.alfred.kmanager.domain.model.ListResult
import es.alfred.kmanager.domain.usecaseapi.OperationsUseCase
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class OperationsUseCaseImpl : OperationsUseCase {
    private val logger = KotlinLogging.logger {}
    private val mongoDAO = DataFactory.getMongoDAO()

    override suspend fun isAlive(): BooleanResult {
        var result: BooleanResult

        try {
            val resp: ServerAlive = this.mongoDAO.checkServerIsAlive()
            result = BooleanResult(resp.isalive)
        }
        catch (err: Exception) {
            logger.error { "Error in OperationsUseCase - isAlive -> $err" }
            result = BooleanResult(false)
        }

        return result
    }

    override suspend fun addBranch(project: String, branchName: String): ListResult {
        val result = ListResult(listOf(), false)

        try {
            val branchesResult = this.mongoDAO.getBranches(project)

            if(branchesResult.result &&
               branchesResult.branches.isNotEmpty() &&
               branchesResult.branches.contains(branchName) &&
               branchesResult.branches.indexOf(branchName) == 0) {

                result.result = true
                result.data = branchesResult.branches
            }
            else {
                var branches = mutableListOf<String>()
                var doInsert = true
                if(branchesResult.branches.isNotEmpty()) {
                    branches = branchesResult.branches.toMutableList()
                    doInsert = false
                }
                if(branches.contains(branchName)) {
                    branches.remove(branchName)
                }
                branches.addFirst(branchName)
                while(branches.size > 10) {
                    branches.removeLast()
                }

                val resp = when(doInsert) {
                    true  -> this.mongoDAO.addBranch(project, branches)
                    false -> this.mongoDAO.updateBranches(project, branches)
                }

                if(resp.result) {
                    result.data = branches
                    result.result = true
                }
            }
        }
        catch (err: Exception) {
            logger.error { "Error in OperationsUseCase - addBranch -> $err" }
            result.result = false
        }

        logger.info { "addBranch -> result: $result" }
        return result
    }

    override suspend fun getBranches(project: String): ListResult {
        val result = ListResult(listOf(), true)

        try {
            val branchesResult = this.mongoDAO.getBranches(project)

            if(branchesResult.result && branchesResult.branches.isNotEmpty()) {
                result.data = branchesResult.branches
            }
        }
        catch (err: Exception) {
            logger.error { "Error in OperationsUseCase - getBranches -> $err" }
            result.result = false
        }

        logger.info { "getBranches -> result: $result" }
        return result
    }
}