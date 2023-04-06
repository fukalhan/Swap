package cz.cvut.fukalhan.swap.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.auth.domain.SignUpUserUseCase
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUserUseCase: SignUpUserUseCase) : ViewModel() {

    fun signUpUser(email: String, username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUserUseCase.signUpUser(SignUpCredentials(email, password, username))
        }
    }
}
