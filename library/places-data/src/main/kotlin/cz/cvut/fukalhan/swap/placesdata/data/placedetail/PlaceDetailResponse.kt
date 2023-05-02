package cz.cvut.fukalhan.swap.placesdata.data.placedetail

import com.google.gson.annotations.SerializedName

data class PlaceDetailResponse(
    @SerializedName("result")
    val result: Result
)

data class Result(
    @SerializedName("geometry")
    val geometry: Geometry
)

data class Geometry(
    @SerializedName("location")
    val location: Coordinates,
    @SerializedName("viewport")
    val viewPort: ViewPort
)

data class ViewPort(
    @SerializedName("northeast")
    val northeast: Coordinates,
    @SerializedName("southwest")
    val southwest: Coordinates
)
