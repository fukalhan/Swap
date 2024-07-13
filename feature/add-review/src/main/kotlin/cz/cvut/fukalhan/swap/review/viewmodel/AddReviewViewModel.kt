package cz.cvut.fukalhan.swap.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.presentation.hideAllOverlays
import cz.cvut.fukalhan.design.presentation.showLoading
import cz.cvut.fukalhan.swap.review.mapper.toVo
import cz.cvut.fukalhan.swap.review.model.AddReviewScreenData
import cz.cvut.fukalhan.swap.review.model.AddReviewScreenEvent
import cz.cvut.fukalhan.swap.userdata.data.resolve
import cz.cvut.fukalhan.swap.userdata.domain.AddReviewUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import cz.cvut.fukalhan.swap.userdata.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddReviewViewModel(
    private val params: Params,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val addReviewUseCase: AddReviewUseCase,
) : ComposeViewModel<AddReviewScreenData, AddReviewScreenEvent>,
    ViewModel() {

    private val _viewState = MutableStateFlow(
        UiState(
            data = AddReviewScreenData()
        )
    )
    override val viewState: StateFlow<UiState<AddReviewScreenData>> = _viewState.asStateFlow()

    override fun onEvent(event: AddReviewScreenEvent) {
        when (event) {
            AddReviewScreenEvent.OnBackClick -> params.navigateBack
            AddReviewScreenEvent.OnUserProfileClick -> params.navigateToUserProfile
            is AddReviewScreenEvent.OnRatingChange -> changeRating(newValue = event.newValue)
            is AddReviewScreenEvent.OnReviewChange -> changeReview(newValue = event.newValue)
            AddReviewScreenEvent.OnSaveReviewClick -> addReview()
            AddReviewScreenEvent.OnCancelReviewClick -> params.navigateBack
        }
    }

    init {
        getUserInfo()
    }

    /**
     * Load review user basic information
     */
    private fun getUserInfo() {
        _viewState.showLoading()
        viewModelScope.launch(Dispatchers.IO) {
            getUserDataUseCase
                .getUserData(params.reviewedUserId)
                .resolve(
                    onSuccess = { user ->
                        _viewState.update {
                            UiState(
                                data = user.toVo()
                            )
                        }
                    },
                    onError = {
                        // TODO
                    }
            )
        }
    }

    /**
     * Update rating
     */
    private fun changeRating(newValue: Int) {
        if (newValue in AddReviewScreenData.MIN_RATING..AddReviewScreenData.MAX_RATING) {
            _viewState.update {
                UiState(
                    data = it.data.copy(
                        rating = newValue
                    )
                )
            }
        }
    }

    /**
     * Change review description
     */
    private fun changeReview(newValue: String) {
        if (newValue.length <= AddReviewScreenData.REVIEW_CHAR_LIMIT) {
            _viewState.update {
                UiState(
                    data = it.data.copy(
                        review = newValue
                    )
                )
            }
        }
    }

    /**
     * Add review after all information are filled
     */
    private fun addReview() {
        val user = Firebase.auth.currentUser

        if (user != null && viewState.value.data.allFieldsFilled) {
            _viewState.showLoading()

            viewModelScope.launch(Dispatchers.IO) {
                val review = Review(
                    userId = params.reviewedUserId,
                    reviewerId = user.uid,
                    rating = viewState.value.data.rating,
                    description = viewState.value.data.review
                )
                addReviewUseCase
                    .addReview(review = review)
                    .resolve(
                        onSuccess = {
                            _viewState.hideAllOverlays()
                            // TODO show success dialog
                            params.navigateBack()
                        },
                        onError = {
                            _viewState.hideAllOverlays()
                            // TODO show error dialog
                            params.navigateBack()
                        }
                    )
            }
        }
    }

    data class Params(
        val reviewedUserId: String,
        val navigateBack: () -> Unit,
        val navigateToUserProfile: () -> Unit
    )
}
