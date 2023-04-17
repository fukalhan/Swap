package cz.cvut.fukalhan.swap.itemlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(private val getItemsUseCase: GetItemsUseCase) : ViewModel() {
    private val _items = MutableStateFlow(ItemListState())
    val items: StateFlow<ItemListState>
        get() = _items

    init {
        val user = Firebase.auth.currentUser
        user?.let {
            getItems(it.uid)
        }
    }

    private fun getItems(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getItemsUseCase.getItems(uid)
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
