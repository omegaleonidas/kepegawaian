package sidiq.project.kepegawaian.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
import sidiq.project.kepegawaian.ViewModel.RegisterViewModel
import sidiq.project.kepegawaian.databinding.FragmentLoginBinding
import sidiq.project.kepegawaian.model.DataItem
import sidiq.project.kepegawaian.model.login.DataUserRespon
import sidiq.project.kepegawaian.model.login.UserRequest

class LoginFragment : Fragment() {

    lateinit var navControler: NavController

    private val LoginDetail = MutableLiveData<sidiq.project.kepegawaian.model.Response>()
    private var viewModel: RegisterViewModel? = null
    var binding: FragmentLoginBinding? = null
    private var shareferenceManager : PreferenceManager? =null
    private var item: DataItem? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        shareferenceManager = PreferenceManager(requireContext())
        var view = binding?.root
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        //   viewModel?.getData()
        viewModel?.registerMVVMModel?.observe(this, Observer {

        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)

        btnLogin.setOnClickListener {
            login()
           
        }

    }

//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.btnLogin ->
//            {
//                login()
//                navControler.navigate(R.id.action_loginFragment_to_home2)
//            }
//
//
//
//        }
//    }

    fun login() {
        val request = UserRequest()
    val  email = binding?.etNip!!.text.toString()
      val password = binding?.etpassword!!.text.toString()

        val retrofit = ApiServices.restApi()
        retrofit.getLogin(email,password).enqueue(object :Callback<DataUserRespon>{
            override fun onFailure(call: Call<DataUserRespon>, t: Throwable) {
              Log.e("error",t.message)
            }

            override fun onResponse(call: Call<DataUserRespon>, response: Response<DataUserRespon>) {
               val user = response.body()
                Log.e("token",response.message())

            if (response.isSuccessful){

                val user = response.body()
//
                if (response.isSuccessful){

                    Log.e("token1","${user?.user!!.id}")

                    shareferenceManager?.saveToken(KEY_TOKEN,user?.token!!)
                    shareferenceManager?.saveNip(NIP,user?.user!!.nip!!)
                    shareferenceManager?.saveId(ID,user?.user!!.id!!)
                    shareferenceManager?.saveNoHp(NOHP,user?.user!!.nohp!!)
                    shareferenceManager?.saveNama(NAMAPEGAWAI,user?.user.name)

                    navControler.navigate(R.id.action_loginFragment_to_loginOTP)


                  //  val intent = Intent(requireContext(),Home::class.java)
//                    startActivity(intent)
//                    requireActivity().finish()
//
                }

            }else{
                Toast.makeText(requireContext(), " data tidak ada", Toast.LENGTH_SHORT).show()
             Log.e("error","data tidak ada")
            }
            }
//
//

        })



    }




}