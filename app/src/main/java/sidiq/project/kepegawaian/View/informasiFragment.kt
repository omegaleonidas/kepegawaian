package sidiq.project.kepegawaian.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.model.informasi.InformasiResponse
import javax.security.auth.callback.Callback

class informasiFragment : Fragment() {


    private var sharedPreferences : PreferenceManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = PreferenceManager(requireContext())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getData()
        super.onViewCreated(view, savedInstanceState)
    }

    fun getData(){

        var retrofit = ApiServices.restApi()
        Log.e("token ",""+sharedPreferences?.getToken())
        retrofit.getInformasi("bearer "+sharedPreferences?.getToken() ).enqueue(object : retrofit2.Callback<InformasiResponse>{
            override fun onFailure(call: Call<InformasiResponse>, t: Throwable) {
               Log.e("error",t.message)
            }

            override fun onResponse(
                call: Call<InformasiResponse>,
                response: Response<InformasiResponse>
            ) {
                val informasi = response.body()

                if (response.isSuccessful){
                    Toast.makeText(requireContext(), ""+informasi?.data, Toast.LENGTH_SHORT).show()




                }
            }

        })


    }




}