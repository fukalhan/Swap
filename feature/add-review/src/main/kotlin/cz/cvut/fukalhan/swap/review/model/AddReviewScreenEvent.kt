package cz.cvut.fukalhan.swap.review.model

import cz.cvut.fukalhan.swap.review.view.AddReviewScreen

/**
 * Events for [AddReviewScreen]
 */
sealed interface AddReviewScreenEvent {

    /**
     * Event to navigate back from the screen
     */
    data object OnBackClick : AddReviewScreenEvent

    /**
     * Event on the reviewed user profile click
     */
    data object OnUserProfileClick : AddReviewScreenEvent

    /**
     * Event on rating changed
     *
     * @property newValue new rating
     */
    data class OnRatingChange(val newValue: Int) : AddReviewScreenEvent

    /**
     * Event on review description change
     *
     * @property newValue new review description
     */
    data class OnReviewChange(val newValue: String) : AddReviewScreenEvent

    /**
     * Event on cancel button click
     */
    data object OnCancelReviewClick : AddReviewScreenEvent

    /**
     * Event on save button click
     */
    data object OnSaveReviewClick : AddReviewScreenEvent
}