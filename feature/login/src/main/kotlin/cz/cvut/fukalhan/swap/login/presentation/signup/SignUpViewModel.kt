package cz.cvut.fukalhan.swap.login.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import cz.cvut.fukalhan.swap.auth.domain.SignUpUseCase
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpCredentials
import cz.cvut.fukalhan.swap.login.presentation.common.Init
import cz.cvut.fukalhan.swap.login.presentation.common.Loading
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _signUpState: MutableStateFlow<LoginState> = MutableStateFlow(Init)
    val signUpState: StateFlow<LoginState>
        get() = _signUpState

    fun signUpUser(email: String, username: String, password: String) {
        _signUpState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val token = FirebaseMessaging.getInstance().token.await()
            val signUpResult = signUpUseCase.signUpUser(SignUpCredentials(email, username, password, token))
            _signUpState.value = signUpResult.toLoginState()
        }
    }

    fun setStateToInit() {
        _signUpState.value = Init
    }
}
