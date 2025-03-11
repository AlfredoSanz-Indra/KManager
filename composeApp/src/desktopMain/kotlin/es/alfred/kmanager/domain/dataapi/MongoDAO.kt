package es.alfred.kmanager.domain.dataapi

import es.alfred.kmanager.data.mongo.entity.BranchesResult
import es.alfred.kmanager.data.mongo.entity.ServerAlive
import es.alfred.kmanager.domain.model.InsertResult

/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface MongoDAO {

    suspend fun checkServerIsAlive(): ServerAlive


}