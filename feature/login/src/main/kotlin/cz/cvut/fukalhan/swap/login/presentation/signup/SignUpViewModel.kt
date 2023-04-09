package cz.cvut.fukalhan.swap.login.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.auth.domain.SignUpUseCase
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpCredentials
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _signUpState = MutableStateFlow(LoginState())
    val signUpState: StateFlow<LoginState>
        get() = _signUpState

    fun signUpUser(email: String, username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val signUpResult = signUpUseCase.signUpUser(SignUpCredentials(email, username, password))
            _signUpState.value = signUpResult.toLoginState()
        }
    }

    fun setSignUpStateNeutral() {
        _signUpState.value = LoginState(LoginState.State.PENDING)
    }
}
