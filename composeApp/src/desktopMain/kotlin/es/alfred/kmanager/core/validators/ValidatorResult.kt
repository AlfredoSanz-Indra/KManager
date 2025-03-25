package es.alfred.kmanager.core.validators

/**
 * @author Alfredo Sanz
 * @time 2025
 */
sealed class ValidatorResult {
    class Error(val message: String) : ValidatorResult()
    object NoResult : ValidatorResult()
    object Success : ValidatorResult()
}