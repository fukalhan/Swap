package cz.cvut.fukalhan.swap.profile.presentation.useritems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserItemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserItemsViewModel(private val getUserItemsUseCase: GetUserItemsUseCase) : ViewModel() {
    private val _items = MutableStateFlow(ItemListState())
    val items: StateFlow<ItemListState>
        get() = _items

    init {
        val user = Firebase.auth.currentUser
        user?.let {
            getUserItems(it.uid)
        }
    }

    private fun getUserItems(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserItemsUseCase.getUserItems(uid)
            if (response.success) {
                response.data?.let { items ->
                    _items.value = ItemListState(
                        items.map {
                            it.toItemState()
                        }
                    )
                }
            }
        }
    }
}
