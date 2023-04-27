package cz.cvut.fukalhan.swap.settings.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.ChangeProfilePictureUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserProfileDataUseCase
import cz.cvut.fukalhan.swap.userdata.domain.UpdateBioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val changeProfilePictureUseCase: ChangeProfilePictureUseCase,
    private val updateBioUseCase: UpdateBioUseCase
) : ViewModel() {
    private val _settingsState: MutableStateFlow<SettingsState> = MutableStateFlow(Init)
    val settingsState: StateFlow<SettingsState>
        get() = _settingsState

    fun getUserData(userId: String) {
        _settingsState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserProfileDataUseCase.getUserProfileData(userId)
            response.data?.let {
                _settingsState.value = it.toUserData()
            } ?: run {
                _settingsState.value = Failure()
            }
        }
    }

    fun changeProfilePicture(userId: String, uri: Uri) {
        _settingsState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = changeProfilePictureUseCase.changeProfilePic(userId, uri)
            if (response.flag == ResponseFlag.SUCCESS) {
                _settingsState.value = ProfilePictureChangeSuccess()
            } else {
                _settingsState.value = ProfilePictureChangeFailed()
            }
            getUserData(userId)
        }
    }

    fun updateBio(userId: String, bio: String) {
        _settingsState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = updateBioUseCase.updateUserBio(userId, bio)
            if (response.flag == ResponseFlag.SUCCESS) {
                _settingsState.value = BioChangeSuccess()
            } else {
                _settingsState.value = BioChangeFailed()
            }
            getUserData(userId)
        }
    }
}
