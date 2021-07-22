package sidiq.project.kepegawaian.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import sidiq.project.kepegawaian.MainActivity
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.KEY_TOKEN
import sidiq.project.kepegawaian.databinding.FragmentInformasiBinding
import sidiq.project.kepegawaian.databinding.FragmentProfileBinding
import sidiq.project.kepegawaian.model.pegawai.PegawaiRespon


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private var sharedPreferences: PreferenceManager? = null
    lateinit var auth: FirebaseAuth
    lateinit var navControler: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        sharedPreferences = PreferenceManager(requireContext())

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

//        Reference

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControler = Navigation.findNavController(view)


        GetData()
        btnEdit.setOnClickListener {

            Toast.makeText(requireContext(), " btn di klik", Toast.LENGTH_SHORT).show()

        }




//        btnLogout.setOnClickListener {
//
//            sharedPreferences?.saveToken(KEY_TOKEN, null)
//
//
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//
//            auth.signOut()
//
//            // navControler.navigate(R.id.action_navigation_profile_to_loginOTP2)
//        }


    }


    fun GetData() {
        val retrofit = ApiServices.restApi()
        retrofit.getPegawai(4,"Bearer " + sharedPreferences?.getToken()).enqueue(object : retrofit2.Callback<PegawaiRespon>{
            override fun onFailure(call: Call<PegawaiRespon>, t: Throwable) {
                Log.e("",t.message)
            }

            override fun onResponse(call: Call<PegawaiRespon>, response: Response<PegawaiRespon>) {

                var respon = response.body()
                if (response.isSuccessful){
                    Log.e("data pegawai masuk" , "$respon")

                    binding?.tvEmail!!.setText(respon!!.data.pegawai.email)
                    binding?.tvAgama!!.setText(respon!!.data.pegawai.nama_agama)
                    binding?.tvAlamat!!.setText(respon!!.data.pegawai.alamat)
                    binding?.tvTanggalMasuk1!!.setText(respon!!.data.pegawai.tgl_masuk)
                    binding?.tvTelepon!!.setText(respon!!.data.pegawai.no_tlp)
                    binding?.tvNama!!.setText(respon!!.data.pegawai.nama_pegawai)
                    binding?.tvNip!!.setText(respon!!.data.pegawai.nip)
                }else{
                    Log.e("data tidak masuk" , "$respon")
                }
            }

        })
    }

}
