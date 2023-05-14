package cz.cvut.fukalhan.swap.events.presentation.prediction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.placesdata.data.resolve
import cz.cvut.fukalhan.swap.placesdata.domain.GetPlacesPredictionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PredictionListViewModel(
    private val getPlacesPredictionsUseCase: GetPlacesPredictionsUseCase
) : ViewModel() {
    private val _predictionListState: MutableStateFlow<PredictionListState> = MutableStateFlow(
        PredictionListState.Init
    )
    val predictionListState: StateFlow<PredictionListState>
        get() = _predictionListState

    fun getPredictions(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getPlacesPredictionsUseCase.getPredictions(address).resolve(
                onSuccess = {
                    if (it.predictions.isNotEmpty()) {
                        _predictionListState.value = it.toPredictionListState()
                    } else {
                        _predictionListState.value = PredictionListState.Empty()
                    }
                },
                onError = { _predictionListState.value = PredictionListState.Failure() }
            )
        }
    }
}
