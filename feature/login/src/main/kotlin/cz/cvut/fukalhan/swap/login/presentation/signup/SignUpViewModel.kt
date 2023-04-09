package cz.cvut.fukalhan.swap.login.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.auth.domain.SignUpUseCase
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState>
        get() = _signUpState

    fun signUpUser(email: String, username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val signUpResponse = signUpUseCase.signUpUser(SignUpCredentials(email, username, password))
            _signUpState.value = signUpResponse.toSignUpState()
        }
    }

    fun setSignUpStateNeutral() {
        _signUpState.value = SignUpState(SignUpState.State.BEFORE_SIGN_UP)
    }
}
