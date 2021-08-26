package sidiq.project.kepegawaian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import sidiq.project.kepegawaian.databinding.ActivityDetailInformasiBinding
import sidiq.project.kepegawaian.databinding.FragmentInformasiBinding

class Detail_informasi : AppCompatActivity() {

    private var binding: ActivityDetailInformasiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInformasiBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        val title = intent.getStringExtra("judul")
        val image = intent.getStringExtra("image")
        val desc = intent.getStringExtra("descripsi")

        Glide.with(this).load("http://192.168.1.8/api/public/foto_informasi/"+image).into(binding!!.image)
        //  Log.e("data masuk","$title,$image,$desc")

        binding?.tvJudul!!.setText(title)
        binding?.tvDescripsi!!.setText(desc)
    }
}