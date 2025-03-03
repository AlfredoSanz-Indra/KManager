package es.alfred.kmanager.core.resources

import es.alfred.kmanager.core.model.Projects
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.InputStream
import kotlinx.serialization.json.*

/**
 * @author Alfredo Sanz
 * @time 2025
 */

@OptIn(ExperimentalSerializationApi::class)
fun readJsonResources_projects(fileName: String): Projects {
    val loader = Thread.currentThread().contextClassLoader
    val input: InputStream = loader.getResourceAsStream(fileName)

    val result = Json.decodeFromStream<Projects>(input)
    return result
}
