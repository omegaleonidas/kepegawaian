package sidiq.project.kepegawaian.model.absensi

data class absensiResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val absensi: List<Absensi>
)


data class Absensi(
    val alamat: String,
    val alamat_sore: String,
    val id_absensi: Int,
    val nip: Int,
    val jam_masuk: String,
    val jam_selesai: String,
    val keterangan: String,
    val keterangan_sore: String,
    val tanggal: String
)