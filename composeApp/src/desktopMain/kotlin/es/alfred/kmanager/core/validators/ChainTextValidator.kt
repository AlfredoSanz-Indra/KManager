package es.alfred.kmanager.core.validators

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class ChainTextValidator(private vararg val validators: TextValidator) : TextValidator {

    override var validatorResult: ValidatorResult = ValidatorResult.NoResult

    override fun validate(theText: String): ValidatorResult {
        validators.forEach { validator ->
            validatorResult = validator.validate(theText)

            if (validatorResult is ValidatorResult.Error) {
                return validatorResult
            }
        }
        return validatorResult
    }
}