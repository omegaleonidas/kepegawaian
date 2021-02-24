package sidiq.project.kepegawaian.model

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("data")
    val data: List<DataItem>,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)