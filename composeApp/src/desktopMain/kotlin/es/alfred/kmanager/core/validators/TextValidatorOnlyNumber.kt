package es.alfred.kmanager.core.validators

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class TextValidatorOnlyNumber : TextValidator {

    override var validatorResult: ValidatorResult = ValidatorResult.NoResult

    override fun validate(theText: String): ValidatorResult {
        validatorResult = when {
            theText.isEmpty() ->
                ValidatorResult.Error("The field value cannot be empty")

            !onlyHasNumberChars(theText) ->
                ValidatorResult.Error("The field only accept numbers")

            else                          ->
                ValidatorResult.Success
        }

        return validatorResult
    }

    private fun onlyHasNumberChars(txt: String) : Boolean {
        var result = false
        if (TextValidatorCommon.matchPattern(txt, "^[0-9]*\$")) {
            result = true
        }
        return result
    }
}