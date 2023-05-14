package cz.cvut.fukalhan.swap.settings.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.userdata.data.resolve
import cz.cvut.fukalhan.swap.userdata.domain.ChangeProfilePictureUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import cz.cvut.fukalhan.swap.userdata.domain.UpdateBioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val changeProfilePictureUseCase: ChangeProfilePictureUseCase,
    private val updateBioUseCase: UpdateBioUseCase
) : ViewModel() {
    private val _settingsState: MutableStateFlow<SettingsState> = MutableStateFlow(Init)
    val settingsState: StateFlow<SettingsState>
        get() = _settingsState

    fun getUserData(userId: String) {
        _settingsState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getUserDataUseCase.getUserData(userId).resolve(
                onSuccess = { _settingsState.value = it.toUserData() },
                onError = { _settingsState.value = Failure() }
            )
        }
    }

    fun changeProfilePicture(userId: String, uri: Uri) {
        _settingsState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            changeProfilePictureUseCase.changeProfilePic(userId, uri).resolve(
                onSuccess = { _settingsState.value = ProfilePictureChangeSuccess() },
                onError = { _settingsState.value = ProfilePictureChangeFailed() }
            )
            getUserData(userId)
        }
    }

    fun updateBio(userId: String, bio: String) {
        _settingsState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            updateBioUseCase.updateUserBio(userId, bio).resolve(
                onSuccess = { _settingsState.value = BioChangeSuccess() },
                onError = { _settingsState.value = BioChangeFailed() }
            )
            getUserData(userId)
        }
    }
}
