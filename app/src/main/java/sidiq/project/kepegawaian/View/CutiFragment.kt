package sidiq.project.kepegawaian.View

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_cuti.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.ViewModel.CutiViewModel
import sidiq.project.kepegawaian.databinding.FragmentCutiBinding
import sidiq.project.kepegawaian.model.cuti.CutiResponse

import java.util.*

class CutiFragment : androidx.fragment.app.Fragment() {

    var viewmodelCuti: CutiViewModel? = null
    var textview_date: TextView? = null
    var binding: FragmentCutiBinding? = null

    var cal = Calendar.getInstance()
    private var sharedPreferences: PreferenceManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = PreferenceManager(requireContext())
        binding = FragmentCutiBinding.inflate(inflater, container, false)

        var view = binding?.root

        textview_date = this.tanggalAwal
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewmodelCuti = ViewModelProviders.of(this).get(CutiViewModel::class.java)
//        viewmodelCuti?.cutiMVVMmodel?.observe(this, Observer {

//
//
//        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        tanggalAwal.setOnClickListener{

                val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    tanggalAwal .setText("" + year + "-" + month + "-" + dayOfMonth)
                }, year, month, day)
                dpd.show()

            }
        tanggalAkhir.setOnClickListener{

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                tanggalAkhir .setText("" + year + "-" + month + "-" + dayOfMonth)
            }, year, month, day)
            dpd.show()

        }


        btnCuti.setOnClickListener {


            insertData()

            Toast.makeText(context, " sudah di klik", Toast.LENGTH_SHORT).show()

        }


        }










    fun insertData() {


        val nip =  sharedPreferences!!.getNip()
        val lama_cuti = 2
        val alasan_cuti = binding?.tanggalKeterangan?.text.toString()


        var tanggal_awal = binding?.tanggalAwal?.text.toString()
       // var awal = tanggal_awal.toInt()
        var tanggal_akhir = binding?.tanggalAkhir?.text.toString()
      //  var akhir = tanggal_akhir.toInt()
        var calender = Calendar.getInstance()
        val time = java.text.DateFormat.getDateTimeInstance().format(calender.time)

        Log.e("data tanggal  ","$time")


        var retrofit = ApiServices.restApi()
        retrofit.InsertCuti(
            nip!!,
            tanggal_awal,
            tanggal_akhir,
            alasan_cuti,
            time,
            lama_cuti,
            "Bearer " + sharedPreferences?.getToken()
        )
            .enqueue(object : Callback<CutiResponse> {

                override fun onFailure(call: Call<CutiResponse>, t: Throwable) {
                    Log.e("data simpan tidak masuk", t.message)
                }

                override fun onResponse(
                    call: Call<CutiResponse>,
                    response: Response<CutiResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "data telah di kirim", Toast.LENGTH_SHORT)
                            .show()


                    } else {
                        Log.e("data tidak response", "${response.message()}")
                    }
                }

            })


    }






}