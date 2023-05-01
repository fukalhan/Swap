package cz.cvut.fukalhan.swap.placesdata.system

import cz.cvut.fukalhan.swap.placesdata.domain.GooglePlacesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitBuilder {
    private const val BASE_URL = "https://maps.googleapis.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: GooglePlacesApiService = getRetrofit().create(GooglePlacesApiService::class.java)
}
