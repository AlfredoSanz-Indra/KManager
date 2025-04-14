package es.alfred.kmanager.core.validators

import mu.KotlinLogging

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class DateGreaterValidator {

    var validatorResult: ValidatorResult = ValidatorResult.NoResult

    fun validate(startYear: Int, endYear: Int, startMonth: Int, endMonth: Int, startDay: Int, endDay: Int): ValidatorResult {
        val valResult1 = checkout(startYear, endYear)
        val valResult2 = checkout(startMonth, endMonth)
        val valResult3 = checkout(startDay, endDay)
        val results = listOf(valResult1, valResult2, valResult3)

        validatorResult = when {
            results[0] < 3 && results[1] == 1 -> ValidatorResult.Success
            results[0] < 3 && results[1] == 2 && results[2] < 3 -> ValidatorResult.Success
            else -> ValidatorResult.Error("End date must be greater than Start date")
        }

        return validatorResult
    }

    private fun checkout(start: Int, end: Int): Int {
        return when {
            start < end -> 1
            start == end -> 2
            else -> 3
        }
    }


}