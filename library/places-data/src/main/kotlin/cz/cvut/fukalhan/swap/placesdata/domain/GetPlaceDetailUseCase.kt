package cz.cvut.fukalhan.swap.placesdata.domain

import cz.cvut.fukalhan.swap.placesdata.data.Response
import cz.cvut.fukalhan.swap.placesdata.data.placedetail.PlaceDetailResponse

class GetPlaceDetailUseCase(private val placesRepository: PlacesRepository) {

    suspend fun getPlaceDetail(placeId: String): Response<PlaceDetailResponse> {
        return placesRepository.getPlaceDetail(placeId)
    }
}
