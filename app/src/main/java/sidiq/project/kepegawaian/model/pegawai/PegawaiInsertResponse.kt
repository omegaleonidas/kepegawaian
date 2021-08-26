package sidiq.project.kepegawaian.model.pegawai

data class PegawaiInsertResponse(
    val `data1`: Data,
    val message: String,
    val success: Boolean
)

data class Data1(
    val alamat_pegawai: String,
    val created_at: String,
    val email: String,
    val gender: String,
    val id_agama: String,
    val id_pegawai: Int,
    val jabatan_id: String,
    val nama_pegawai: String,
    val nip: String,
    val no_tlp: Long,
    val pendidikan: String,
    val tgl_masuk: String,
    val tmp_lahir: String,
    val updated_at: String
)