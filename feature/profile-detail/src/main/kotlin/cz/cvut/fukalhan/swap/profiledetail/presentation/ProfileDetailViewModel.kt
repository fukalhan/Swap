package cz.cvut.fukalhan.swap.profiledetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.userdata.domain.GetUserProfileDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileDetailViewModel(
    private val stringResources: StringResources,
    private val getUserProfileDetailUseCase: GetUserProfileDetailUseCase
) : ViewModel() {
    private val _profileDetailState: MutableStateFlow<ProfileDetailState> = MutableStateFlow(Init)
    val profileDetailState: StateFlow<ProfileDetailState>
        get() = _profileDetailState

    fun getUserProfile(userId: String) {
        _profileDetailState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserProfileDetailUseCase.getUserProfileDetail(userId)
            response.data?.let {
                _profileDetailState.value = it.toProfileDetailState(stringResources)
            } ?: run {
                _profileDetailState.value = Failure()
            }
        }
    }
}
