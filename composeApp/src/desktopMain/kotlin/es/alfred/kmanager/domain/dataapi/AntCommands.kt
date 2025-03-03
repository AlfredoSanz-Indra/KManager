package es.alfred.kmanager.domain.dataapi

import es.alfred.kmanager.domain.model.AntResult


/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface AntCommands {

    fun execAntGitCheckout(antTaskName: String, destBranch: String, microID: String): AntResult

    fun execAntGitCommandSmart(antTaskName: String,  microID: String): AntResult

    fun execAntGitCommand(antTaskName: String): AntResult;

    fun execAntGitCommandSmartPush(destBranch: String): AntResult

    fun execAntNodeCommandsSmart(antTaskName: String,  microID: String): AntResult

    fun execAntNodeCommands(antTaskName: String): AntResult;

    suspend fun runMongoServer(): AntResult

    suspend fun openMongoShell(): AntResult

    suspend fun execTest(input: String): AntResult
}