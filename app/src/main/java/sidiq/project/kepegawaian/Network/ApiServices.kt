package sidiq.project.kepegawaian.Network
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServices {

    companion object {

        val baseUrl = "http://192.168.1.8:8000/api/"
        fun restApi(): ApiInterface{

            val intercepton = HttpLoggingInterceptor()
            intercepton.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttp = OkHttpClient().newBuilder().addInterceptor(intercepton).build()

            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

    fun restApiLogin():Retrofit{
        val  gson= GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }



}