package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrganizerInfoViewModel(
    private val stringResources: StringResources,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {
    private val _organizerInfoState: MutableStateFlow<OrganizerInfoState> = MutableStateFlow(OrganizerInfoState.Init)
    val organizerInfoState: StateFlow<OrganizerInfoState>
        get() = _organizerInfoState

    fun getOrganizerData(userId: String) {
        _organizerInfoState.value = OrganizerInfoState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserDataUseCase.getUserData(userId)
            response.data?.let {
                _organizerInfoState.value = it.toOrganizerInfoState(stringResources)
            } ?: run {
                _organizerInfoState.value = OrganizerInfoState.Failure()
            }
        }
    }
}
