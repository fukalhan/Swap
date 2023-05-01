package cz.cvut.fukalhan.swap.placesdata.data

import android.util.Log
import cz.cvut.fukalhan.swap.placesdata.data.predictions.GooglePredictionsResponse
import cz.cvut.fukalhan.swap.placesdata.domain.GooglePlacesApiService
import cz.cvut.fukalhan.swap.placesdata.domain.PlacesRepository

internal class GooglePlacesRepository(private val api: GooglePlacesApiService) : PlacesRepository {

    override suspend fun getPredictions(input: String): Response<GooglePredictionsResponse> {
        return try {
            val response = api.getPredictions(input = input)
            Response.Success(response)
        } catch (e: Exception) {
            Log.e("getPlacesPredictions", "Exceptiop $e")
            Response.Error()
        }
    }
}
