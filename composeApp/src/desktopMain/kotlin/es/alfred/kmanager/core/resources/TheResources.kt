package es.alfred.kmanager.core.resources

import es.alfred.kmanager.core.model.Resources


/**
 * @author Alfredo Sanz
 * @time 2025
 */
object TheResources {

    private lateinit var resources: Resources

    fun getResources(): Resources {
        if (!this::resources.isInitialized) {
            this.resources = readJsonResources_resources("resources.json")
        }
        return this.resources
    }
}