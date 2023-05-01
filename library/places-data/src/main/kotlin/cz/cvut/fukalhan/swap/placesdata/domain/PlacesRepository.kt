package cz.cvut.fukalhan.swap.placesdata.domain

import cz.cvut.fukalhan.swap.placesdata.data.Response
import cz.cvut.fukalhan.swap.placesdata.data.predictions.GooglePredictionsResponse

interface PlacesRepository {
    suspend fun getPredictions(input: String): Response<GooglePredictionsResponse>
}
