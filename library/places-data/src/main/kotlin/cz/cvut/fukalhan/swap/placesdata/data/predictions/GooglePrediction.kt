package cz.cvut.fukalhan.swap.placesdata.data.predictions

import com.google.gson.annotations.SerializedName

data class GooglePrediction(
    @SerializedName("description")
    val description: String,
    @SerializedName("place_id")
    val placeId: String
)
