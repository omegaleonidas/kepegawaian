package sidiq.project.kepegawaian.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class DataItem(

    @field:SerializedName("nip")
    val nip: Int,

    @field:SerializedName("no_hp")
    val noHp: Int,

    @field:SerializedName("updated_at")
    val updatedAt: Any,

    @field:SerializedName("created_at")
    val createdAt: Any,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String


)