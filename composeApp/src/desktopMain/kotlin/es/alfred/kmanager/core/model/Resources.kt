package es.alfred.kmanager.core.model

import kotlinx.serialization.Serializable

/**
 * @author Alfredo Sanz
 * @time 2025
 */
@Serializable
data class Resources(@Serializable() val projects: List<Project>,
                     @Serializable()val mongo: Mongo )