package cz.cvut.fukalhan.swap.profiledetail.presentation.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.userdata.data.resolve
import cz.cvut.fukalhan.swap.userdata.domain.GetUserReviewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewListViewModel(
    private val getUserReviewsUseCase: GetUserReviewsUseCase
) : ViewModel() {
    private val _reviewListState: MutableStateFlow<ReviewListState> = MutableStateFlow(ReviewListState.Init)
    val reviewListState: StateFlow<ReviewListState>
        get() = _reviewListState

    fun getReviews(userId: String) {
        _reviewListState.value = ReviewListState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getUserReviewsUseCase.getReviews(userId).resolve(
                onSuccess = {
                    if (it.isNotEmpty()) {
                        _reviewListState.value = ReviewListState.DataLoaded(
                            it.map { review ->
                                review.toReviewState()
                            }
                        )
                    } else {
                        _reviewListState.value = ReviewListState.Empty()
                    }
                },
                onError = { _reviewListState.value = ReviewListState.Failure() }
            )
        }
    }
}
