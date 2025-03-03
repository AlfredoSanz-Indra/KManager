package es.alfred.kmanager.domain.usecaseapi

import es.alfred.kmanager.domain.model.AntResult

/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface AntUseCase {

    suspend fun gitCheckout(microFs: List<String>, destBranch: String)

    suspend fun gitPullAll();

    suspend fun gitBranch();

    suspend fun gitPullList(microFs: List<String>);

    suspend fun gitPush(destBranch: String)

    suspend fun nodeRunMicroF(microF: String)

    suspend fun nodeRunMicroFList(microFs: List<String>)

    suspend fun nodeRunTestMicroF(microF: String)

    suspend fun nodeRunTestMicroFList(microFs: List<String>)

    suspend fun mongoRunServer()

    suspend fun openMongoShell()

    suspend fun coroutineTest(input: String)

    suspend fun coroutineTestReturn(input: String): AntResult;
}