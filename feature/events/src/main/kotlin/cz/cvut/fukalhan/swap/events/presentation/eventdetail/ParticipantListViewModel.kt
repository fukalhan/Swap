package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.userdata.domain.GetUserListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ParticipantListViewModel(
    private val stringResources: StringResources,
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    private val _participantListState: MutableStateFlow<ParticipantListState> = MutableStateFlow(
        ParticipantListState.Init
    )
    val participantListState: StateFlow<ParticipantListState>
        get() = _participantListState

    fun getParticipantList(userIds: List<String>) {
        _participantListState.value = ParticipantListState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserListUseCase.getUsersById(userIds)
            response.data?.let { users ->
                if (users.isNotEmpty()) {
                    _participantListState.value = ParticipantListState.Success(
                        participantsCount = stringResources.getString(R.string.participantsCount, users.size),
                        participants = users.map { user ->
                            user.toParticipantInfo()
                        }
                    )
                } else {
                    _participantListState.value = ParticipantListState.Empty()
                }
            } ?: run {
                _participantListState.value = ParticipantListState.Failure()
            }
        }
    }
}
