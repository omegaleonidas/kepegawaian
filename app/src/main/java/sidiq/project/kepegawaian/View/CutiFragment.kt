package sidiq.project.kepegawaian.View

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast
import com.cazaea.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.fragment_cuti.*
import okhttp3.internal.format
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.Storage.PreferenceManager

import sidiq.project.kepegawaian.databinding.FragmentCutiBinding
import sidiq.project.kepegawaian.model.cuti.CutiResponse

import java.util.*

class CutiFragment : androidx.fragment.app.Fragment() {


    var textview_date: TextView? = null
    lateinit var alertDialog: SweetAlertDialog
    var binding: FragmentCutiBinding? = null

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

//

    }

    val timer = object : CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {


        }

        override fun onFinish() {
            cancel()
            alertDialog.dismiss()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Bagus")
            .setContentText("anda sudah mengajukan cuti")

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




        val tz: TimeZone? = TimeZone.getTimeZone("GMT+7")
        val alasan_cuti = binding?.tanggalKeterangan?.text.toString()
        val c = Calendar.getInstance(tz)

        val tanggal_awal = binding?.tanggalAwal?.text.toString()

        val tanggal_akhir = binding?.tanggalAkhir?.text.toString()

        val calender = Calendar.getInstance()
        val time = java.text.DateFormat.getDateTimeInstance().format(calender.time)
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val date =
            StringBuilder().append(year).append("-").append(month).append("-").append(day).toString()









            Log.e("data tanggal  ","$time")


        val retrofit = ApiServices.restApi()
        retrofit.InsertCuti(
            nip!!,
            tanggal_awal,
            tanggal_akhir,
            alasan_cuti,
            date,

            "Bearer " + sharedPreferences?.getToken()
        )
            .enqueue(object : Callback<CutiResponse> {

                override fun onFailure(call: Call<CutiResponse>, t: Throwable) {

                    SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("  jaringan tidak ada atau gangguan   ")
                        .setConfirmText("OK")
                        .show()
                }

                override fun onResponse(
                    call: Call<CutiResponse>,
                    response: Response<CutiResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "data telah di kirim", Toast.LENGTH_SHORT)
                            .show()
                        alertDialog.show()
                        timer.start()

                    } else {

                    }
                }

            })


    }






}