package sidiq.project.kepegawaian.model.informasi

data class InformasiResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Informasi(
    val descripsi: String,
    val gambar: String,
    val id_informasi: Int,
    val judul: String
)

data class Data(
    val informasi: List<Informasi>
)