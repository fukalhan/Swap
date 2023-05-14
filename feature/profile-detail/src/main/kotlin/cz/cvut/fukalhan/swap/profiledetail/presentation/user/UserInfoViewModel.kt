package cz.cvut.fukalhan.swap.profiledetail.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.userdata.data.resolve
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val stringResources: StringResources,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {
    private val _userInfoState: MutableStateFlow<UserInfoState> = MutableStateFlow(UserInfoState.Init)
    val userInfoState: StateFlow<UserInfoState>
        get() = _userInfoState

    fun getUserInfo(userId: String) {
        _userInfoState.value = UserInfoState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getUserDataUseCase.getUserData(userId).resolve(
                onSuccess = { _userInfoState.value = it.toUserState(stringResources) },
                onError = { _userInfoState.value = UserInfoState.Failure() }
            )
        }
    }
}
