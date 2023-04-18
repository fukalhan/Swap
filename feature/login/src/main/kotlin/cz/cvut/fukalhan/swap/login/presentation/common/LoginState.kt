package cz.cvut.fukalhan.swap.login.presentation.common

sealed class LoginState

object Init : LoginState()
object Loading : LoginState()
object Success : LoginState()
class Failure(val message: Int) : LoginState()
