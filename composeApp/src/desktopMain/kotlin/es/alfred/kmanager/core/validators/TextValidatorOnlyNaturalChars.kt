package es.alfred.kmanager.core.validators

/**
 * @author Alfredo Sanz
 * @time 2025
 */
class TextValidatorOnlyNaturalChars: TextValidator {

    override var validatorResult: ValidatorResult = ValidatorResult.NoResult

    override fun validate(theText: String): ValidatorResult {
        validatorResult = when {
            theText.isEmpty() ->
                ValidatorResult.Error("The field value cannot be empty")

            !onlyHasNaturalChars(theText) ->
                ValidatorResult.Error("The field has not allowed chars")

            else                          ->
                ValidatorResult.Success
        }

        return validatorResult
    }

    private fun onlyHasNaturalChars(txt: String) : Boolean {
        var result = false
        if (TextValidatorCommon.matchPattern(txt, "^[a-zA-Z0-9_-]*\$")) {
            result = true
        }
        return result
    }
}