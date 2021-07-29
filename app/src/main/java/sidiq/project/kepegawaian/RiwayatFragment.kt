package sidiq.project.kepegawaian

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_riwayat.*
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.ViewModel.AbsensiViewModel
import sidiq.project.kepegawaian.adapter.AdapterApsensi
import sidiq.project.kepegawaian.adapter.AdapterInformasi
import sidiq.project.kepegawaian.databinding.ActivityMainBinding
import sidiq.project.kepegawaian.databinding.FragmentRiwayatBinding
import sidiq.project.kepegawaian.model.absensi.Absensi
import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi
import sidiq.project.kepegawaian.model.absensi.absensiResponse
import sidiq.project.kepegawaian.model.informasi.Informasi

class RiwayatFragment : Fragment() {

    private var binding: FragmentRiwayatBinding? = null
    private var viewModel: AbsensiViewModel? = null
    private var sharedPreferences: PreferenceManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = PreferenceManager(requireContext())
        binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    fun getData() {
        val retrofit = ApiServices.restApi()
        retrofit.getRiwayatabsensi(sharedPreferences?.getId()!!, "Bearer " + sharedPreferences?.getToken()).enqueue(object : retrofit2.Callback<absensiResponse> {
            override fun onFailure(call: Call<absensiResponse>, t: Throwable) {
                Log.e("",t.message)
            }

            override fun onResponse(
                call: Call<absensiResponse>,
                response: Response<absensiResponse>
            ) {

                var riwayat = response.body()
                if (response.isSuccessful){
                    Log.e("data masuk","${riwayat?.data}")
                    showData(riwayat!!.data?.absensi)

                }else{
                    Log.e("data tidak terespone","${response.message()}" )
                }


            }

        })


    }

    fun showData(riwayat: List<Absensi>) {

        binding?.recylerRiwayat?.apply {
            binding?.recylerRiwayat?.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterApsensi(riwayat)

        }


    }

}





