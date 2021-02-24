package sidiq.project.kepegawaian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sidiq.project.kepegawaian.ViewModel.RegisterViewModel

class MainActivity : AppCompatActivity() {


    private var viewModel: RegisterViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}