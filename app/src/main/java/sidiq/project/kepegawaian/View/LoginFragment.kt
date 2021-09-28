package sidiq.project.kepegawaian.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cazaea.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.ID
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.KEY_TOKEN
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.NAMAPEGAWAI
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.NIP
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.NOHP

import sidiq.project.kepegawaian.databinding.FragmentLoginBinding
import sidiq.project.kepegawaian.model.DataItem
import sidiq.project.kepegawaian.model.login.DataUserRespon


class LoginFragment : Fragment() {

    lateinit var navControler: NavController

    private val LoginDetail = MutableLiveData<sidiq.project.kepegawaian.model.Response>()

    var binding: FragmentLoginBinding? = null
    private var shareferenceManager : PreferenceManager? =null
    private var item: DataItem? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        shareferenceManager = PreferenceManager(requireContext())
        val view = binding?.root
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)

        btnLogin.setOnClickListener {
            login()
           
        }

    }



    fun login() {

    val  email = binding?.etNip!!.text.toString()
      val password = binding?.etpassword!!.text.toString()

        val retrofit = ApiServices.restApi()
        retrofit.getLogin(email,password).enqueue(object :Callback<DataUserRespon>{
            override fun onFailure(call: Call<DataUserRespon>, t: Throwable) {

            }

            override fun onResponse(call: Call<DataUserRespon>, response: Response<DataUserRespon>) {

                Log.e("token",response.message())

            if (response.isSuccessful){

                val user = response.body()
//
                if (response.isSuccessful){




                    shareferenceManager?.saveToken(KEY_TOKEN,user!!.token!!)
                    shareferenceManager?.saveNip(NIP,user!!.user?.nip!!)
                    shareferenceManager?.saveId(ID,user!!.user?.id!!)
                    shareferenceManager?.saveNoHp(NOHP,user!!.user?.nohp!!)
                    shareferenceManager?.saveNama(NAMAPEGAWAI,user!!.user?.name)

                    navControler.navigate(R.id.action_loginFragment_to_loginOTP)


                  //  val intent = Intent(requireContext(),Home::class.java)
//                    startActivity(intent)
//                    requireActivity().finish()
//
                }

            }else{
                SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Maaf")
                    .setContentText("username dan password salah ")
                    .setConfirmText("OK")
                    .show()
             Log.e("error","data tidak ada")
            }
            }
//
//

        })



    }




}