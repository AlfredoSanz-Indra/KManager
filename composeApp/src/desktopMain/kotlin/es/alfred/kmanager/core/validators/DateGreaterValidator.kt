package es.alfred.kmanager.core.validators

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
            results.contains(3) -> {
                ValidatorResult.Error("The End time must be greater than Start time")
            }
            else ->
                ValidatorResult.Success
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