package es.alfred.kmanager.core.validators


/**
 * @author Alfredo Sanz
 * @time 2025
 */
class TextValidatorPassword: TextValidator {

    override var validatorResult: ValidatorResult = ValidatorResult.NoResult

    override fun validate(theText: String): ValidatorResult {
        validatorResult = when {
            theText.isEmpty() ->
                ValidatorResult.Error("The field value cannot be empty")

            !isValidPassword(theText) ->
                ValidatorResult.Error("The password is not well formed")

            else                      ->
                ValidatorResult.Success
        }

        return validatorResult
    }

    /**
        Must meet at least three constraints
     */
    private fun isValidPassword(password: String) : Boolean {
        var count = 0
        if (TextValidatorCommon.matchPattern(password, ".*\\d.*")) {
            count++
        }
        if (TextValidatorCommon.matchPattern(password, ".*[a-z].*")) {
            count++
        }
        if (TextValidatorCommon.matchPattern(password, ".*[A-Z].*")) {
            count++
        }
        return count >= 3
    }
}