package sidiq.project.kepegawaian.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import sidiq.project.kepegawaian.MainActivity
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.loginOTP

class Flashscreen : AppCompatActivity() {

    lateinit var sharedPreferences: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashscreen)
        sharedPreferences = PreferenceManager(this)



        Handler().postDelayed(Runnable {


            if (sharedPreferences.isloged()) {

                startActivity(Intent(this, Home::class.java))
                finish()

            } else if (sharedPreferences.getToken() != null && !sharedPreferences.isloged()) {

                startActivity(Intent(this, loginOTP::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()


            }

        }, 5000)
        title = ""


    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}
