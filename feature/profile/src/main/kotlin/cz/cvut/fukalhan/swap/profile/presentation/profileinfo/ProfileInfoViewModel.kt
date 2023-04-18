package cz.cvut.fukalhan.swap.profile.presentation.profileinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.userdata.domain.GetUserProfileDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileInfoViewModel(
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val stringResources: StringResources
) : ViewModel() {
    private val _profileInfoState: MutableStateFlow<ProfileInfoState> = MutableStateFlow(Init)
    val profileInfoState: StateFlow<ProfileInfoState>
        get() = _profileInfoState

    init {
        val user = Firebase.auth.currentUser
        user?.let {
            initProfile(it.uid)
        }
    }

    private fun initProfile(uid: String) {
        _profileInfoState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val userProfileResponse = getUserProfileDataUseCase.getUserProfileData(uid)
            userProfileResponse.data?.let {
                _profileInfoState.value = it.toProfileInfoState(stringResources)
            } ?: run {
                _profileInfoState.value = Failure()
            }
        }
    }
}
