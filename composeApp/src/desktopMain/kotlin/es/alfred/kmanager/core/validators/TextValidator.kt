package es.alfred.kmanager.core.validators

/**
 * @author Alfredo Sanz
 * @time 2025
 */
interface TextValidator {
    fun validate(theText: String): ValidatorResult
    var validatorResult: ValidatorResult
}