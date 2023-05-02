package cz.cvut.fukalhan.swap.placesdata.domain

import cz.cvut.fukalhan.swap.placesdata.BuildConfig
import cz.cvut.fukalhan.swap.placesdata.data.placedetail.PlaceDetailResponse
import cz.cvut.fukalhan.swap.placesdata.data.predictions.GooglePredictionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService {
    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("key") key: String = BuildConfig.GOOGLE_MAPS_API_KEY,
        @Query("types") types: String = "address",
        @Query("input") input: String
    ): GooglePredictionsResponse

    @GET("maps/api/place/details/json")
    suspend fun getPlaceDetails(
        @Query("key") apiKey: String = BuildConfig.GOOGLE_MAPS_API_KEY,
        @Query("place_id") placeId: String,
    ): PlaceDetailResponse
}
