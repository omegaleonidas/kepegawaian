package sidiq.project.kepegawaian.model.pegawai

data class PegawaiRespon(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Pegawai(
    val alamat: String,
    val created_at: String,
    val email: String,
    val foto: String,
    val gender: String,
    val id_agama: Int,
    val id_jabatan: Int,
    val id_pegawai: Int,
    val jabatan_id: Int,
    val nama_agama: String,
    val nama_jabatan: String,
    val nama_pegawai: String,
    val nip: String,
    val no_tlp: String,
    val password: Any,
    val pendidikan: String,
    val tgl_masuk: String,
    val tmp_lahir: String,
    val updated_at: String
)

data class Data(
    val pegawai: Pegawai
)