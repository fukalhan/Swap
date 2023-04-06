package cz.cvut.fukalhan.swap.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.auth.domain.SignUpUserUseCase
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUserUseCase: SignUpUserUseCase) : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState>
        get() = _signUpState

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val signUpResponse = signUpUserUseCase.signUpUser(SignUpCredentials(email, password))
            _signUpState.value = signUpResponse.toSignUpState()
        }
    }
}
