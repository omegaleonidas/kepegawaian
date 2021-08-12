package sidiq.project.kepegawaian.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Response
import sidiq.project.kepegawaian.MainActivity
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.ID
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.KEY_TOKEN
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

        Log.v("data id", "" + sharedPreferences?.getToken())

        GetData()
//


        btnEdit.setOnClickListener {


        }






        btnLogout.setOnClickListener {

            sharedPreferences?.saveToken(KEY_TOKEN, null)
            sharedPreferences?.saveId(ID, 0)

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

            auth.signOut()

            // navControler.navigate(R.id.action_navigation_profile_to_loginOTP2)
        }


    }


    fun GetData() {
        val retrofit = ApiServices.restApi()
        Log.e("data id", "" + sharedPreferences?.getToken())
        retrofit.getPegawai(sharedPreferences?.getId()!!, "Bearer " + sharedPreferences?.getToken())
            .enqueue(object : retrofit2.Callback<PegawaiRespon> {
                override fun onFailure(call: Call<PegawaiRespon>, t: Throwable) {
                    Log.e("", t.message)
                }

                override fun onResponse(
                    call: Call<PegawaiRespon>,
                    response: Response<PegawaiRespon>
                ) {


                    val data = response.body()?.data
                    if (response.isSuccessful) {
                        Log.e("data pegawai masuk", "$data")

                        if (data?.pegawai != null){
                            binding?.tvEmail!!.setText(data.pegawai.email)
                            binding?.tvAgama!!.setText(data.pegawai.nama_agama)
                            binding?.tvAlamat!!.setText(data.pegawai.alamat)
                            binding?.tvTanggalMasuk1!!.setText(data.pegawai.tgl_masuk)
                            binding?.tvTelepon!!.setText(data.pegawai.no_tlp)
                            binding?.tvNama!!.setText(data.pegawai.nama_pegawai)
                            binding?.tvNip!!.setText(data.pegawai.nip)
                            binding?.tvPendidikanTerakhir!!.setText(data.pegawai.pendidikan)
                        }else{
                            Toast.makeText(requireContext(), "data belum di tambah", Toast.LENGTH_SHORT).show()
                        }
                        
                    } else {
                        Log.e("data tidak masuk", "$data")
                    }
                }

            })
    }


    fun Edit() {

    }

}
