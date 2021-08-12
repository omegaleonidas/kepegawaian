package sidiq.project.kepegawaian.model.absensiInsert

data class AbsensiInsertResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val alamat: String,
    val created_at: String,
    val id_absensi: Int,
    val jam_masuk: String,
    val jam_selesai: Any,
    val keterangan: String,
    val nip: String,
    val tanggal: String,
    val updated_at: String
)