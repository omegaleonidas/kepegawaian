package sidiq.project.kepegawaian.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_informasi.view.*
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.adapter.AdapterInformasi
import sidiq.project.kepegawaian.databinding.FragmentInformasiBinding
import sidiq.project.kepegawaian.model.informasi.Informasi
import sidiq.project.kepegawaian.model.informasi.InformasiResponse

class informasiFragment : Fragment() {

    private var binding: FragmentInformasiBinding? = null
    private var sharedPreferences: PreferenceManager? = null
    private var adapter: AdapterInformasi? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = PreferenceManager(requireContext())

        // Inflate the layout for this fragment
        binding = FragmentInformasiBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getData()

        super.onViewCreated(view, savedInstanceState)
    }

    fun getData() {

        var retrofit = ApiServices.restApi()
        Log.e("token ", "" + sharedPreferences?.getToken())
        retrofit.getInformasi("Bearer " + sharedPreferences?.getToken())
            .enqueue(object : retrofit2.Callback<InformasiResponse> {
                override fun onFailure(call: Call<InformasiResponse>, t: Throwable) {
                    Log.e("error", t.message)
                }

                override fun onResponse(
                    call: Call<InformasiResponse>,
                    response: Response<InformasiResponse>
                ) {
                    val informasi = response.body()

                    if (response.isSuccessful) {

                        showData(informasi!!.data?.informasi)
                    }
                }

            })


    }

    fun showData(informasi: List<Informasi>) {

        binding?.recyclerViewInformasi?.apply {
            binding?.recyclerViewInformasi?.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterInformasi(informasi)

        }


    }


}


