package cz.cvut.fukalhan.swap.login.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.auth.domain.SignInUseCase
import cz.cvut.fukalhan.swap.auth.model.signin.SignInCredentials
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {
    private val _signInState = MutableStateFlow(LoginState())
    val signInState: StateFlow<LoginState>
        get() = _signInState

    fun signInUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val signInResult = signInUseCase.signInUser(SignInCredentials(email, password))
            _signInState.value = signInResult.toLoginState()
        }
    }

    fun setSignInStateNeutral() {
        _signInState.value = LoginState(LoginState.State.PENDING)
    }
}
