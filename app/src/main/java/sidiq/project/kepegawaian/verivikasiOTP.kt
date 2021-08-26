package sidiq.project.kepegawaian

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.Storage.PreferenceManager.Companion.LOGIN
import sidiq.project.kepegawaian.View.Home
import kotlin.math.log

class verivikasiOTP : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private var sharedPreferences: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verivikasi_o_t_p)
        sharedPreferences = PreferenceManager(this)
        auth = FirebaseAuth.getInstance()


       // intent.getStringExtra("storedVerificationId")
        val storedVerificationId = intent.getStringExtra("storedVerificationId")
        Log.e("TAG", "onCreate: "+intent.getStringExtra("storedVerificationId"))


//        Reference
        val verify = findViewById<Button>(R.id.verifyBtn)
        val otpGiven = findViewById<EditText>(R.id.id_otp)

        verify.setOnClickListener {
            var otp = otpGiven.text.toString().trim()
            if (!otp.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    sharedPreferences!!.saveLoginState(LOGIN,true)
                    startActivity(Intent(applicationContext, Home::class.java))
                    finish()

                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}