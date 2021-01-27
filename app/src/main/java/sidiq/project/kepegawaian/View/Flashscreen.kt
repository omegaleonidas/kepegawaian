package sidiq.project.kepegawaian.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import sidiq.project.kepegawaian.MainActivity
import sidiq.project.kepegawaian.R

class Flashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashscreen)



        Handler().postDelayed(Runnable {

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }, 5000)
        title = ""


    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
    }
