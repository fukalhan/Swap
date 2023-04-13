package cz.cvut.fukalhan.swap.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserProfileDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val getUserProfileDataUseCase: GetUserProfileDataUseCase) : ViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState>
        get() = _profileState

    init {
        val user = Firebase.auth.currentUser
        user?.let {
            initProfile(it.uid)
        }
    }

    private fun initProfile(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userProfileResponse = getUserProfileDataUseCase.getUserProfileData(uid)
            userProfileResponse.data?.let {
                _profileState.value = it.toProfileState()
            }
        }
    }
}
