package sidiq.project.kepegawaian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sidiq.project.kepegawaian.Storage.PreferenceManager
import sidiq.project.kepegawaian.View.Home


class MainActivity : AppCompatActivity() {


    private var sharedPreferences : PreferenceManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sharedPreferences = PreferenceManager(this)





    }

    override fun onStart() {



        super.onStart()
//


    }

}