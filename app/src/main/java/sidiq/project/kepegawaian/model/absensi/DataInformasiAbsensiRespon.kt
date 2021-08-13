package sidiq.project.kepegawaian.model.absensi

data class DataInformasiAbsensiRespon(
    val `data`: DataX,
    val message: String,
    val success: Boolean
)
data class DataX(
    val alfa: Int,
    val cuti: Int,
    val hadir: Int,
    val terlambat: Int,
    val hadir_sore: Int,
    val terlambat_sore: Int,
    val alfa_sore: Int
)