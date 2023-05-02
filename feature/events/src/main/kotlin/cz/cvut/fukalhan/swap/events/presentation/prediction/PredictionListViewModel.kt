package cz.cvut.fukalhan.swap.events.presentation.prediction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.placesdata.data.Response
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
            val response = getPlacesPredictionsUseCase.getPredictions(address)
            when (response) {
                is Response.Success -> {
                    response.data?.let {
                        if (it.predictions.isNotEmpty()) {
                            _predictionListState.value = it.toPredictionListState()
                        } else {
                            _predictionListState.value = PredictionListState.Empty()
                        }
                    }
                }
                else -> _predictionListState.value = PredictionListState.Failure()
            }
        }
    }

    fun setStateToInit() {
        _predictionListState.value = PredictionListState.Init
    }
}
