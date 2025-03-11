package es.alfred.kmanager.domain.usecase

import es.alfred.kmanager.core.di.DataFactory
import es.alfred.kmanager.domain.dataapi.AntCommands
import es.alfred.kmanager.domain.model.AntResult
import es.alfred.kmanager.domain.usecaseapi.AntUseCase
import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class AntUseCaseImpl: AntUseCase {
    private val logger = KotlinLogging.logger {}
    private val antCommand: AntCommands = DataFactory.getAntCommands()

    override suspend fun gitCheckout(microF: String, destBranch: String) {
        val r: AntResult = this.antCommand.execAntGitCheckout("git-checkout", destBranch, microF)
        logger.info { "\"AntCommand - gitCheckout  result=${r.result}" }
    }

    override suspend fun gitPush(microF: String, destBranch: String) {
        var r: AntResult = this.antCommand.execAntGitCommandSmartPush("git-push", microF, destBranch)
        logger.info { "\"AntCommand - git-push $destBranch - result=${r.result}" }
    }

    override suspend fun gitPullAll() {
        val r: AntResult = this.antCommand.execAntGitCommand("git-pull-all")
        logger.info { "\"AntCommand - gitPullAll  result=${r.result}" }
    }

    override suspend fun gitBranch() {
        val r: AntResult = this.antCommand.execAntGitCommand("git-branch")
        logger.info { "\"AntCommand - gitBranch  result=${r.result}" }
    }

    override suspend fun gitPullList(microFs: List<String>) {
        microFs.forEach { it ->
            var r: AntResult = this.antCommand.execAntGitCommandSmart("git-pull-smart",  it)
            logger.info { "\"AntCommand - gitPullList - result=${r.result}" }
        }
        logger.info { "\"executed Pull for microFrontales ($microFs)" }
    }

    override suspend fun nodeRunMicroF(microF: String) {
        var r: AntResult = this.antCommand.execAntNodeCommandsSmart("run-smart", microF)
        logger.info { "\"AntCommand - nodeRun $microF - result=${r.result}" }
    }

    override suspend fun nodeRunMicroFList(microFs: List<String>) {
        microFs.forEach { it ->
            var r: AntResult = this.antCommand.execAntNodeCommandsSmart("run-smart", it)
            logger.info { "\"AntCommand - nodeRun - result=${r.result}" }
        }
        logger.info { "\"executed Node Run for microFrontales ($microFs)" }
    }

    override suspend fun nodeRunTestMicroF(microF: String) {
        var r: AntResult = this.antCommand.execAntNodeCommandsSmart("run-test", microF)
        logger.info { "\"AntCommand - nodeRun $microF - result=${r.result}" }
    }

    override suspend fun nodeRunTestMicroFList(microFs: List<String>) {
        microFs.forEach { it ->
            var r: AntResult = this.antCommand.execAntNodeCommandsSmart("run-test", it)
            logger.info { "\"AntCommand - nodeRun - result=${r.result}" }
        }
        logger.info { "\"executed Node Run for microFrontales ($microFs)" }
    }

    override suspend fun mongoRunServer() {
        val r: AntResult = this.antCommand.runMongoServer()
        logger.info { "\"AntCommand - Mongo - result=${r.result}" }
    }

    override suspend fun coroutineTest(input: String) {
        val r: AntResult = this.antCommand.execTest(input)
        logger.info { "\"No command - test  result=${r.result}" }
    }

    override suspend fun openMongoShell() {
        val r: AntResult = this.antCommand.openMongoShell()
        logger.info { "\"AntCommand - Mongosh - result=${r.result}" }
    }

    override suspend fun coroutineTestReturn(input: String): AntResult {
        val r: AntResult = this.antCommand.execTest(input)
        logger.info { "\"No command - test  result=${r.result}" }

        return r
    }
}