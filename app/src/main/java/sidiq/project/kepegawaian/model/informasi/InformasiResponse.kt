package sidiq.project.kepegawaian.model.informasi

import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi

data class InformasiResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Informasi(
    @field:SerializedName("descripsi")
    val descripsi: String? = null,
    @field:SerializedName("gambar")
    val gambar: String? = null,
    @field:SerializedName("id_informasi")
    val id_informasi: Int? = null,
    @field:SerializedName("judul")
    val judul: String? = null


)

data class Data(
    val informasi: List<Informasi>
)