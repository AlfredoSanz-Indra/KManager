package es.alfred.kmanager.core.model

import kotlinx.serialization.Serializable

/**
 * @author Alfredo Sanz
 * @time 2025
 */
@Serializable
data class Project(
    val name: String,
    val label: String,
    val task: String,
    val runnable: Boolean
    )
