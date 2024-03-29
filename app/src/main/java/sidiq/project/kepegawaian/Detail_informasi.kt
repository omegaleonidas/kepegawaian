package sidiq.project.kepegawaian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.bumptech.glide.Glide
import sidiq.project.kepegawaian.databinding.ActivityDetailInformasiBinding


class Detail_informasi : AppCompatActivity() {

    private var binding: ActivityDetailInformasiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInformasiBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        val title = intent.getStringExtra("judul")
        val image = intent.getStringExtra("image")
        val desc = intent.getStringExtra("descripsi")

        Glide.with(this).load("http://34.101.240.57/public/foto_informasi/"+image).into(binding!!.image)
        //  Log.e("data masuk","$title,$image,$desc")

        binding?.tvJudul!!.setText(title)
        binding?.tvDescripsi!!.setText(desc)
    }
}