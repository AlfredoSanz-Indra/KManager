package es.alfred.kmanager.core.model

import kotlinx.serialization.Serializable

/**
 * @author Alfredo Sanz
 * @time 2025
 */
@Serializable
data class Projects(@Serializable() val projects: List<Project> )