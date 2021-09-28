package sidiq.project.kepegawaian.Network

import io.reactivex.rxjava3.core.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import sidiq.project.kepegawaian.model.absensi.DataInformasiAbsensiRespon

import sidiq.project.kepegawaian.model.absensi.absensiResponse
import sidiq.project.kepegawaian.model.absensiInsert.AbsensiInsertResponse
import sidiq.project.kepegawaian.model.cuti.CutiResponse
import sidiq.project.kepegawaian.model.informasi.InformasiResponse
import sidiq.project.kepegawaian.model.jam.timeResponse
import sidiq.project.kepegawaian.model.login.DataUserRespon
import sidiq.project.kepegawaian.model.pegawai.PegawaiInsertResponse
import sidiq.project.kepegawaian.model.pegawai.PegawaiRespon
import sidiq.project.kepegawaian.model.user.userResponse


interface ApiInterface {


    @GET("apsensi")
    fun getAbsensi(): Flowable<sidiq.project.kepegawaian.model.absensi.Response>

    //  @FormUrlEncoded
    @POST("login")
    fun getLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<DataUserRespon>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("ApiInformasiShow")
    fun getInformasi(@Header("Authorization") token: String): Call<InformasiResponse>


    //cuti

    @POST("ApiCutiTambah")
    fun InsertCuti(
        @Query("nip") nip: Int,
        @Query("tanggal_mulai") tanggal_mulai: String,
        @Query("tanggal_akhir") tanggal_akhir: String,
        @Query("alasan_cuti") alasan_cuti: String,
        @Query("tanggal") tanggal: String,
        @Header("Authorization") token: String
    )
            : Call<CutiResponse>

    //absensi
    @POST("ApiAbsensi")
    fun InsertAbsensi(

        @Query("nip") nip: Int,
        @Query("tanggal") tanggal: String,
        @Query("jam_masuk") jam_masuk: String,
        @Query("alamat") alamat: String,
        @Query("keterangan") keterangan: String, @Header("Authorization") token: String
    )
            : Call<AbsensiInsertResponse>


    @PUT("ApiAbsensiEdit/{id_absensi}")
    fun InsertAbsensiSore(
        @Path("id_absensi") id_absensi: Int,
        @Query("jam_selesai") jam_selesai: String,
        @Query("alamat_sore") alamat_sore: String,
        @Query("keterangan_sore") keterangan_sore: String, @Header("Authorization") token: String
    )
            : Call<AbsensiInsertResponse>


    @GET("detailAbsensiShow/{id_absensi}")
    fun getRiwayatabsensi(
        @Path("id_absensi") id_absensi: Int,
        @Header("Authorization") token: String
    ): Call<absensiResponse>


    //pegawai
    @GET("pegawaiShow/{id_pegawai}")
    fun getPegawai(
        @Path("id_pegawai") id_pegawai: Int,
        @Header("Authorization") token: String
    ): Call<PegawaiRespon>


    //insert Pegawai
    @Multipart
    @POST("ApiPegawai")
    fun InsertPegawai(

        @Part("nip") nip: RequestBody?,
        @Part("nama_pegawai") nama_pegawai: RequestBody?,
        @Part("jabatan_id") jabatan_id: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("no_tlp") no_tlp: RequestBody?,
        @Part("alamat_pegawai") alamat_pegawai: RequestBody?,
        @Part("tgl_masuk") tgl_masuk: RequestBody?,
        @Part("tmp_lahir") tmp_lahir: RequestBody?,
        @Part("id_agama") ig_agama: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("pendidikan") pendidikan: RequestBody?,
        @Part foto: MultipartBody.Part?,
        @Header("Authorization") token: String
    )
            : Call<PegawaiInsertResponse>


    @Multipart
    @POST("ApiPegawaiEdit/{id_pegawai}")
    fun UpdatePegawai(
        @Path("id_pegawai") id_pegawai: Int,
        @Part("nip") nip: RequestBody?,
        @Part("nama_pegawai") nama_pegawai: RequestBody?,
        @Part("jabatan_id") jabatan_id: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("no_tlp") no_tlp: RequestBody?,
        @Part("alamat_pegawai") alamat_pegawai: RequestBody?,
        @Part("tgl_masuk") tgl_masuk: RequestBody?,
        @Part("tmp_lahir") tmp_lahir: RequestBody?,
        @Part("id_agama") ig_agama: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("pendidikan") pendidikan: RequestBody?,
        @Part foto: MultipartBody.Part?,
        @Header("Authorization") token: String
    )
            : Call<PegawaiInsertResponse>


    @GET("ApiHitung/{id_absensi}")
    fun getInformasiAbsensi(
        @Path("id_absensi") id_absensi: Int,
        @Header("Authorization") token: String
    ): Call<DataInformasiAbsensiRespon>

    @GET("time")
    fun getTime(
        @Header("Authorization") token: String
    ): Call<timeResponse>


    @POST("updateProfile/{id_pegawai}")
    fun UpdateUser(
        @Path("id_pegawai") id_absensi: Int,
        @Query("name") name: String,
        @Query("nip") nip: String,
        @Query("nohp") nohp: String,
        @Query("email") email: String,
        @Query("password") password: String, @Header("Authorization") token: String
    )
            : Call<userResponse>



}