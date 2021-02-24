package sidiq.project.kepegawaian.DataSouce.cuti

data class Data(
    val alasan_cuti: String,
    val id: Int,
    val lama_cuti: Int,
    val nip: Int,
    val tanggal: String,
    val tanggal_acc: String,
    val tanggal_akhir: String,
    val tanggal_mulai: String
)