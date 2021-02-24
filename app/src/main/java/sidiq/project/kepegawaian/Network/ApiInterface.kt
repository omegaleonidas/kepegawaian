package sidiq.project.kepegawaian.Network

import io.reactivex.rxjava3.core.Flowable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import sidiq.project.kepegawaian.DataSouce.cuti.CutiResponse

interface ApiInterface {


    @GET("register")
    fun getUser(): Flowable<sidiq.project.kepegawaian.model.Response>

    @GET("apsensi")
    fun getAbsensi(): Flowable<sidiq.project.kepegawaian.model.absensi.Response>



    @POST("cuti")
    fun InsertCuti(
        @Field("nip") nip: String,
        @Field("tanggal_mulai") tanggal_mulai: String,
        @Field("tanggal_akhir") tanggal_akhir: String,
        @Field("alasan_cuti") alasan_cuti: String,
        @Field("tanggal") tanggal: String
    ): Flowable<CutiResponse>


}