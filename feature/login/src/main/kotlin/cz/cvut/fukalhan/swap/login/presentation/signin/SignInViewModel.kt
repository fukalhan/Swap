package cz.cvut.fukalhan.swap.login.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.auth.domain.SignInUseCase
import cz.cvut.fukalhan.swap.auth.model.SignInCredentials
import cz.cvut.fukalhan.swap.login.presentation.common.Init
import cz.cvut.fukalhan.swap.login.presentation.common.Loading
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {
    private val _signInState: MutableStateFlow<LoginState> = MutableStateFlow(Init)
    val signInState: StateFlow<LoginState>
        get() = _signInState

    fun signInUser(email: String, password: String) {
        _signInState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val signInResult = signInUseCase.signInUser(SignInCredentials(email, password))
            _signInState.value = signInResult.toLoginState()
        }
    }

    fun setStateToInit() {
        _signInState.value = Init
    }
}
