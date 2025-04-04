package es.alfred.kmanager.core.validators

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class TextValidatorLength(val minLength: Int? = null,
                          val maxLength: Int? = null): TextValidator {

    override var validatorResult: ValidatorResult = ValidatorResult.NoResult

    override fun validate(theText: String): ValidatorResult {
        validatorResult = when {
            theText.isEmpty() || theText.trim().isEmpty()            ->
                ValidatorResult.Error("cannot be empty")

            minLength != null && theText.trim().length < minLength ->
                ValidatorResult.Error("cannot have a length of less than $minLength chars")

            maxLength != null && theText.trim().length > maxLength ->
                ValidatorResult.Error("may not be longer than $maxLength chars")

            else ->
                ValidatorResult.Success
        }

        return validatorResult
    }
}