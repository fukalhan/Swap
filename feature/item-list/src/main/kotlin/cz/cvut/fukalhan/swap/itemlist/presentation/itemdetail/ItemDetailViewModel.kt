package cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemDetailUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserProfileDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val getItemDetailUseCase: GetItemDetailUseCase,
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase
) : ViewModel() {
    private val _itemDetailState: MutableStateFlow<ItemDetailState> = MutableStateFlow(Init)
    val itemDetailState: StateFlow<ItemDetailState>
        get() = _itemDetailState

    fun getItemDetail(userId: String, itemId: String) {
        _itemDetailState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getItemDetailUseCase.getItemDetail(userId, itemId).data?.let { itemDetail ->
                getUserProfileDataUseCase.getUserProfileData(itemDetail.ownerId).data?.let { user ->
                    _itemDetailState.value = itemDetail.toItemDetailState(user.id, user.username, user.profilePicUri)
                } ?: run {
                    _itemDetailState.value = Failure()
                }
            } ?: run {
                _itemDetailState.value = Failure()
            }
        }
    }

    fun toggleItemLike(userId: String, itemId: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleItemLikeUseCase.toggleItemLike(userId, itemId, isLiked)
        }
    }
}
