package es.alfred.kmanager.domain.usecaseapi

import es.alfred.kmanager.view.shared.SelectData

/**
 * @author Alfredo Sanz
 * @date 2025
 */
interface TasksContextUseCase {

    suspend fun getCurrentProject(): SelectData

    suspend fun insertUpdateCurrentProject(project: SelectData): SelectData

}