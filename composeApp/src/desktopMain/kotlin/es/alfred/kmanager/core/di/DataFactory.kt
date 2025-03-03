package es.alfred.kmanager.core.di

import es.alfred.kmanager.data.ant.AntCommandsImpl
import es.alfred.kmanager.data.mongo.MongoDAOImpl
import es.alfred.kmanager.domain.dataapi.AntCommands
import es.alfred.kmanager.domain.dataapi.MongoDAO
import es.alfred.kmanager.domain.usecase.AntUseCaseImpl
import es.alfred.kmanager.domain.usecaseapi.AntUseCase

/**
 * @author Alfredo Sanz
 * @time 2025
 */
object DataFactory {

    private lateinit var antCommand: AntCommands
    private lateinit var mongoDAO: MongoDAO

    fun getAntCommands(): AntCommands {
        if (!this::antCommand.isInitialized) {
            this.antCommand = AntCommandsImpl()
        }
        return this.antCommand
    }

    fun getMongoDAO(): MongoDAO {
        if (!this::mongoDAO.isInitialized) {
            this.mongoDAO = MongoDAOImpl()
        }
        return this.mongoDAO
    }
}