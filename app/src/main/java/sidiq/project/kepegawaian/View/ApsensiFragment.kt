package sidiq.project.kepegawaian.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_apsensi.*
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices

import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.databinding.FragmentApsensiBinding

import sidiq.project.kepegawaian.model.absensi.DataInformasiAbsensiRespon
import java.util.*

class ApsensiFragment : Fragment() {

    private var binding: FragmentApsensiBinding? = null
    private var sharedPreferences: PreferenceManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = PreferenceManager(requireContext())

        binding = FragmentApsensiBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GetData()

        val calender = Calendar.getInstance()
        val time = java.text.DateFormat.getDateTimeInstance().format(calender.time)

        binding?.tanggalHome?.text = time



        binding?.textView2?.text = sharedPreferences?.getNama()
        binding?.textView5?.text = sharedPreferences?.getNip().toString()
        btnDetailApsen.setOnClickListener {
            val intent = Intent(context, ApsensiDetail::class.java)
            startActivity(intent)



        }


    }


    val retrofit = ApiServices.restApi()
    private fun GetData() {
        retrofit.getInformasiAbsensi(
            sharedPreferences?.getNip()!!,
            "Bearer " + sharedPreferences?.getToken()
        ).enqueue(object : retrofit2.Callback<DataInformasiAbsensiRespon> {
            override fun onFailure(call: Call<DataInformasiAbsensiRespon>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<DataInformasiAbsensiRespon>,
                response: Response<DataInformasiAbsensiRespon>
            ) {
                val informasidata = response.body()
                if (response.isSuccessful) {

//                    binding?.TvAlfa?.setText(""+informasidata!!.data.alfa)
//                    binding?.TvHadir?.setText(""+informasidata!!.data.hadir)
//                    binding?.TvIzin?.setText(""+informasidata!!.data.cuti)
//                    binding?.TvAlfaSore?.setText(""+informasidata!!.data.alfa_sore)
//                    binding?.TvHadirSore?.setText(""+informasidata!!.data.hadir_sore)
//                    binding?.TvTerlambat?.setText(""+informasidata!!.data.terlambat)
//                    binding?.TvTerlambatSore?.setText(""+informasidata!!.data.terlambat_sore)


                    Log.e("data alfa", "${informasidata!!.data.alfa} ")
                }
            }
        })


    }
}