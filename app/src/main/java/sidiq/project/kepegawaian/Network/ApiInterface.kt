package sidiq.project.kepegawaian.Network

import io.reactivex.rxjava3.core.Flowable
import retrofit2.Call
import retrofit2.http.*
import sidiq.project.kepegawaian.model.absensi.absensiResponse
import sidiq.project.kepegawaian.model.cuti.CutiResponse
import sidiq.project.kepegawaian.model.informasi.InformasiResponse
import sidiq.project.kepegawaian.model.login.DataUserRespon
import sidiq.project.kepegawaian.model.pegawai.PegawaiRespon
import java.util.*

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


    @GET("pegawaiShow/{id_pegawai}")
    fun getPegawai(@Path("id_pegawai") id_pegawai: Int, @Header("Authorization") token:String) :Call<PegawaiRespon>







    @POST("ApiCutiTambah")
    fun InsertCuti(
        @Query("nip") nip: Int,
        @Query("tanggal_mulai") tanggal_mulai: Int,
        @Query("tanggal_akhir") tanggal_akhir: Int,
        @Query("alasan_cuti") alasan_cuti:String,
        @Query("tanggal") tanggal: String,
        @Query("lama_cuti")lama_cuti: Int, @Header("Authorization") token:String)
    : Call<CutiResponse>



    @GET("detailAbsensiShow/{id_absensi}")
    fun getRiwayatabsensi(@Path("id_absensi") id_absensi : Int, @Header("Authorization") token:String) :Call<absensiResponse>

}