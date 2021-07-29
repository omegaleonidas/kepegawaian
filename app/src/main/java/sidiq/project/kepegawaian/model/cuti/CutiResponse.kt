package sidiq.project.kepegawaian.model.cuti

data class CutiResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val alasan_cuti: String,
    val lama_cuti: String,
    val nip: String,
    val tanggal: String,
    val tanggal_akhir: String,
    val tanggal_mulai: String
)