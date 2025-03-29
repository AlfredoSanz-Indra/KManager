package es.alfred.kmanager.domain.dataapi

import es.alfred.kmanager.data.mongo.results.BranchesResult
import es.alfred.kmanager.data.mongo.results.InsertResult


/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface BranchesDAO {

    suspend fun addBranch(project: String, branchList: List<String>): InsertResult

    suspend fun updateBranches(project: String, branchList: List<String>): InsertResult

    suspend fun getBranches(project: String): BranchesResult
}