package cz.cvut.fukalhan.swap.events.presentation.prediction

import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.placesdata.data.predictions.GooglePredictionsResponse

sealed class PredictionListState {
    object Init : PredictionListState()
    object Loading : PredictionListState()
    data class Failure(val message: Int = R.string.predictionsLoadingError) : PredictionListState()
    data class Empty(val message: Int = R.string.emptyPredictions) : PredictionListState()
    data class Success(
        val predictions: List<PredictionState>
    ) : PredictionListState()
}

data class PredictionState(
    val description: String,
    val place_id: String
)

internal fun GooglePredictionsResponse.toPredictionListState(): PredictionListState {
    val predictions = this.predictions.map {
        PredictionState(
            it.description,
            it.place_id
        )
    }
    return PredictionListState.Success(
        predictions
    )
}
