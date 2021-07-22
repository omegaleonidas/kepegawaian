package sidiq.project.kepegawaian.model.absensi

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class Response(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("results")
    val results: Results


)

data class DataItemAbsensi(

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("jam_masuk")
    val jamMasuk: String? = null,

    @field:SerializedName("jam_selesai")
    val jamSelesai: String? = null,

    @field:SerializedName("jam_ke")
    val jamKe: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_pegawai")
    val idPegawai: Int? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null




)

data class Results(

    @field:SerializedName("first_page_url")
    val firstPageUrl: String,

    @field:SerializedName("path")
    val path: String,

    @field:SerializedName("per_page")
    val perPage: Int,

    @field:SerializedName("total")
    val total: Int,



    @field:SerializedName("last_page")
    val lastPage: Int,

    @field:SerializedName("last_page_url")
    val lastPageUrl: String,

    @field:SerializedName("next_page_url")
    val nextPageUrl: Any,

    @field:SerializedName("from")
    val from: Int,

    @field:SerializedName("to")
    val to: Int,

    @field:SerializedName("prev_page_url")
    val prevPageUrl: Any,

    @field:SerializedName("current_page")
    val currentPage: Int,
    @field:SerializedName("data")
    val data: List<DataItemAbsensi>

)