package cz.cvut.fukalhan.swap.review.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.resolve
import cz.cvut.fukalhan.swap.userdata.domain.AddReviewUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import cz.cvut.fukalhan.swap.userdata.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val addReviewUseCase: AddReviewUseCase,
    private val stringResources: StringResources
) : ViewModel() {
    private val _userInfoState: MutableStateFlow<UserInfoState> = MutableStateFlow(UserInfoState.Init)
    val userInfoState: StateFlow<UserInfoState>
        get() = _userInfoState

    private val _addReviewState: MutableStateFlow<AddReviewState> = MutableStateFlow(AddReviewState.Init)
    val addReviewState: StateFlow<AddReviewState>
        get() = _addReviewState

    fun getUserInfo(userId: String) {
        _userInfoState.value = UserInfoState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserDataUseCase.getUserData(userId)
            when (response) {
                is DataResponse.Success -> _userInfoState.value = response.data.toUserInfoState(stringResources)
                is DataResponse.Error -> _userInfoState.value = UserInfoState.Failure()
            }
        }
    }

    fun addReview(
        userId: String,
        reviewerId: String,
        rating: Int,
        description: String
    ) {
        _addReviewState.value = AddReviewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val review = Review(
                userId = userId,
                reviewerId = reviewerId,
                rating = rating,
                description = description
            )
            addReviewUseCase.addReview(review).resolve(
                onSuccess = { _addReviewState.value = AddReviewState.Success() },
                onError = { _addReviewState.value = AddReviewState.Failure() }
            )
        }
    }

    fun resetAddReviewState() {
        _addReviewState.value = AddReviewState.Init
    }
}
