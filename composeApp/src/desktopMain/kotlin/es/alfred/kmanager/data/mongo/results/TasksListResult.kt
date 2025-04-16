package es.alfred.kmanager.data.mongo.results

import es.alfred.kmanager.data.mongo.entity.MngTask

/**
 * @author Alfredo Sanz
 * @date 2025
 */
data class TasksListResult(var result: Boolean, var data: List<MngTask>, var errorMsg: String?)
