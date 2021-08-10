package sidiq.project.kepegawaian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_menu_login.*
import java.util.concurrent.Executors


class menuLogin : Fragment(), View.OnClickListener {

    lateinit var navControler: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_login, container, false)



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)


        btnMenuLogin.setOnClickListener(this)
        btnMenuRegister.setOnClickListener(this)
        val executor = Executors.newSingleThreadExecutor()
        val activity: menuLogin = this // reference to activity
        val biometricPrompt = androidx.biometric.BiometricPrompt(
            activity,
            executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        // user clicked negative button
                    } else {


                    }
                }

                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
              //      navControler.navigate(R.id.action_menuLogin_to_loginOTP)

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                }
            })

        val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("masukkan sidik jari anda.")

            .setNegativeButtonText("kembali")
            .build()

       btnFingger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)

        }


    }




    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnMenuLogin ->
                  navControler.navigate(R.id.action_menuLogin_to_loginFragment)
            R.id.btnMenuRegister ->
                  navControler.navigate(R.id.action_menuLogin_to_registerFragment)

        }
    }
}