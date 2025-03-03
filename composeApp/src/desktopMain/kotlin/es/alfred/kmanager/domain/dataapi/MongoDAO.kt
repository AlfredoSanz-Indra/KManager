package es.alfred.kmanager.domain.dataapi

import es.alfred.kmanager.data.mongo.entity.ServerAlive

/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface MongoDAO {

    suspend fun checkServerIsAlive(): ServerAlive
}