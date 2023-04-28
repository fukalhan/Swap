package cz.cvut.fukalhan.swap.profiledetail.presentation.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            val response = getUserReviewsUseCase.getReviews(userId)
            response.data?.let { reviews ->
                if (reviews.isNotEmpty()) {
                    _reviewListState.value = ReviewListState.DataLoaded(
                        reviews.map { review ->
                            review.toReviewState()
                        }
                    )
                } else {
                    _reviewListState.value = ReviewListState.Empty()
                }
            } ?: run {
                _reviewListState.value = ReviewListState.Failure()
            }
        }
    }
}
