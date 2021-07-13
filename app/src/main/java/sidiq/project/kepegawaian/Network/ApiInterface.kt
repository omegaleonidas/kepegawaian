package sidiq.project.kepegawaian.Network

import androidx.annotation.Nullable
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Call
import retrofit2.http.*
import sidiq.project.kepegawaian.DataSouce.cuti.CutiResponse
import sidiq.project.kepegawaian.model.informasi.InformasiResponse
import sidiq.project.kepegawaian.model.login.UserRequest
import sidiq.project.kepegawaian.model.login.DataUserRespon

interface ApiInterface {


    @GET("register")
    fun getUser(): Flowable<sidiq.project.kepegawaian.model.Response>

    @GET("apsensi")
    fun getAbsensi(): Flowable<sidiq.project.kepegawaian.model.absensi.Response>

    @FormUrlEncoded
    @POST("login")
    fun getLogin(@Field("email")email:String, @Field("password")password:String): Call<DataUserRespon>


    @GET("ApiInformasiShow")
    fun getInformasi(@Header("Authorization")token:String): Call<InformasiResponse>


    @POST("cuti")
    fun InsertCuti(
        @Field("nip") nip: String,
        @Field("tanggal_mulai") tanggal_mulai: String,
        @Field("tanggal_akhir") tanggal_akhir: String,
        @Field("alasan_cuti") alasan_cuti: String,
        @Field("tanggal") tanggal: String
    ): Flowable<CutiResponse>


}