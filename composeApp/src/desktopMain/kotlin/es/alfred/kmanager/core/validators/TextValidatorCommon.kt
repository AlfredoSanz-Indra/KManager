package es.alfred.kmanager.core.validators

/**
 * @author Alfredo Sanz
 * @time 2025
 */
object TextValidatorCommon {

    fun matchPattern(password: String, passwordPattern: String): Boolean {
        password.let {
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        }
    }
}