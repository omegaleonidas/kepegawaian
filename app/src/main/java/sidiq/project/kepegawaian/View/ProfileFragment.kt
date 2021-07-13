package sidiq.project.kepegawaian.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Retrofit
import sidiq.project.kepegawaian.MainActivity
import sidiq.project.kepegawaian.Network.ApiServices
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.KEY_TOKEN


class ProfileFragment : Fragment() {

    private var sharedPreferences : PreferenceManager? = null
    lateinit var auth: FirebaseAuth
    lateinit var navControler: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        sharedPreferences = PreferenceManager(requireContext())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser

//        Reference

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)
        btnLogout.setOnClickListener {

            sharedPreferences?.saveToken(KEY_TOKEN,null)


            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

            auth.signOut()

           // navControler.navigate(R.id.action_navigation_profile_to_loginOTP2)
        }



    }



    fun GetData(){
         val retrofit = ApiServices.restApi()
        retrofit.getAbsensi()
    }

}
