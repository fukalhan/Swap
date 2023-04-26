package cz.cvut.fukalhan.swap.profile.presentation.profileinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.auth.domain.GetStreamChatUserTokenUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserProfileDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileInfoViewModel(
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val getStreamChatUserTokenUseCase: GetStreamChatUserTokenUseCase,
    private val stringResources: StringResources
) : ViewModel() {
    private val _profileInfoState: MutableStateFlow<ProfileInfoState> = MutableStateFlow(Init)
    val profileInfoState: StateFlow<ProfileInfoState>
        get() = _profileInfoState

    private val _chatToken = MutableStateFlow("")
    val chatToken: StateFlow<String>
        get() = _chatToken

    fun initProfile(uid: String) {
        _profileInfoState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val userProfileResponse = getUserProfileDataUseCase.getUserProfileData(uid)
            userProfileResponse.data?.let {
                _profileInfoState.value = it.toProfileInfoState(stringResources)
                if (chatToken.value == "") {
                    getChatToken()
                }
            } ?: run {
                _profileInfoState.value = Failure()
            }
        }
    }

    private fun getChatToken() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = getStreamChatUserTokenUseCase.getChatToken()
            token?.let {
                _chatToken.value = it
            }
        }
    }
}
