package cz.cvut.fukalhan.swap.placesdata.domain

import cz.cvut.fukalhan.swap.placesdata.data.Response
import cz.cvut.fukalhan.swap.placesdata.data.placedetail.PlaceDetailResponse
import cz.cvut.fukalhan.swap.placesdata.data.predictions.GooglePredictionsResponse

interface PlacesRepository {
    suspend fun getPredictions(input: String): Response<GooglePredictionsResponse>

    suspend fun getPlaceDetail(placeId: String): Response<PlaceDetailResponse>
}
