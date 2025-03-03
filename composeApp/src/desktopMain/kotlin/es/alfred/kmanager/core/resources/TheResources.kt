package es.alfred.kmanager.core.resources

import es.alfred.kmanager.core.model.Projects


/**
 * @author Alfredo Sanz
 * @time 2025
 */
object TheResources {

    private lateinit var projects: Projects

    fun getProjects(): Projects {
        if (!this::projects.isInitialized) {
            this.projects = readJsonResources_projects("projects.json")
        }
        return this.projects
    }
}