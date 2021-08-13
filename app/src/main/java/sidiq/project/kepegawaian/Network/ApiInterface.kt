package sidiq.project.kepegawaian.Network

import io.reactivex.rxjava3.core.Flowable
import retrofit2.Call
import retrofit2.http.*

import sidiq.project.kepegawaian.model.absensi.absensiResponse
import sidiq.project.kepegawaian.model.absensiInsert.AbsensiInsertResponse
import sidiq.project.kepegawaian.model.cuti.CutiResponse
import sidiq.project.kepegawaian.model.informasi.InformasiResponse
import sidiq.project.kepegawaian.model.login.DataUserRespon
import sidiq.project.kepegawaian.model.pegawai.PegawaiInsertResponse
import sidiq.project.kepegawaian.model.pegawai.PegawaiRespon


interface ApiInterface {


    @GET("register")
    fun getUser(): Flowable<sidiq.project.kepegawaian.model.Response>

    @GET("apsensi")
    fun getAbsensi(): Flowable<sidiq.project.kepegawaian.model.absensi.Response>

  //  @FormUrlEncoded
    @POST("login")
    fun getLogin(@Query("email")email:String, @Query("password")password:String): Call<DataUserRespon>

    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("ApiInformasiShow")
    fun getInformasi(@Header("Authorization")token:String): Call<InformasiResponse>


  //cuti

    @POST("ApiCutiTambah")
    fun InsertCuti(
        @Query("nip") nip: Int,
        @Query("tanggal_mulai") tanggal_mulai: String,
        @Query("tanggal_akhir") tanggal_akhir: String,
        @Query("alasan_cuti") alasan_cuti:String,
        @Query("tanggal") tanggal: String,
        @Query("lama_cuti")lama_cuti: Int, @Header("Authorization") token:String)
    : Call<CutiResponse>

//absensi
    @POST("ApiAbsensi")
    fun InsertAbsensi(

        @Query("nip") nip: Int,
        @Query("tanggal")tanggal: String,
        @Query("jam_masuk")jam_masuk:String,
        @Query("alamat")alamat:String,
        @Query("keterangan")keterangan:String, @Header("Authorization") token:String)
    :Call<AbsensiInsertResponse>


    @PUT("ApiAbsensiEdit/{id_absensi}")
    fun InsertAbsensiSore(
        @Path("id_absensi") id_absensi: Int,
        @Query("jam_selesai")jam_selesai:String,
        @Query("alamat_sore")alamat_sore:String,
        @Query("keterangan_sore")keterangan_sore:String, @Header("Authorization") token:String)
            :Call<AbsensiInsertResponse>



    @GET("detailAbsensiShow/{id_absensi}")
    fun getRiwayatabsensi(@Path("id_absensi") id_absensi : Int, @Header("Authorization") token:String) :Call<absensiResponse>



    //pegawai
    @GET("pegawaiShow/{id_pegawai}")
    fun getPegawai(@Path("id_pegawai") id_pegawai: Int, @Header("Authorization") token:String) :Call<PegawaiRespon>



//insert Pegawai

    @POST("ApiPegawai")
    fun InsertPegawai(

        @Query("nip") nip: Int,
        @Query("nama_pegawai")nama_pegawai: String,
        @Query("jabatan_id")jabatan_id:Int,
        @Query("email")email:String,
        @Query("no_tlp")no_tlp:String,
        @Query("alamat")alamat:String,
        @Query("tgl_masuk")tgl_masuk:String,
        @Query("tmp_lahir")tmp_lahir:String,
        @Query("id_agama")ig_agama:Int,
        @Query("gender")gender:String,
        @Query("pendidikan")pendidikan:String,
        @Query("foto")foto:String, @Header("Authorization") token:String)
            :Call<PegawaiInsertResponse>


}