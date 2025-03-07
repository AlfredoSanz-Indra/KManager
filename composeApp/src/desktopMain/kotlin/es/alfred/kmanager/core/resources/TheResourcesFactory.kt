package es.alfred.kmanager.core.resources

import es.alfred.kmanager.core.model.Resources
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import mu.KotlinLogging
import java.io.File
import java.io.InputStream
import java.nio.file.Paths

/**
 * @author Alfredo Sanz
 * @time 2025
 */

@OptIn(ExperimentalSerializationApi::class)
fun readJsonResources_resources(fileName: String): Resources {
    val logger = KotlinLogging.logger {}
    val path = Paths.get("").toAbsolutePath().toString()
    logger.info { "readJsonResources_projects -> path: $path, fileName: $fileName" }

    val input: InputStream = File(fileName).inputStream()
    val result = Json.decodeFromStream<Resources>(input)
    return result
}
