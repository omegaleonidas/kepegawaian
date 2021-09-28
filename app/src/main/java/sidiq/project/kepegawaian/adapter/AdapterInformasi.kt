package sidiq.project.kepegawaian.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sidiq.project.kepegawaian.Detail_informasi
import sidiq.project.kepegawaian.databinding.CardInformasiBinding
import sidiq.project.kepegawaian.model.informasi.Informasi


class AdapterInformasi(private  val list: List<Informasi>) : RecyclerView.Adapter<AdapterInformasi.informasiHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = informasiHolder(
        CardInformasiBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    inner class informasiHolder(var binding: CardInformasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: Informasi?) {

            binding.tvJudul.text = item?.judul
            binding.tvDescripsi.text = item?.descripsi
            Glide.with  (binding.gambar.context).load("http://192.168.1.6/api/public/foto_informasi/"+ item?.gambar)
                .into(binding.gambar)


            binding.card.setOnClickListener {

                val intent = Intent(binding.card.context, Detail_informasi::class.java)
                intent.putExtra("judul",item?.judul)
                intent.putExtra("descripsi",item?.descripsi)
                intent.putExtra("image",item?.gambar)
                binding.card.context.startActivity(intent)

            }
        }



    }

    override fun onBindViewHolder(holder: informasiHolder, position: Int) {
      val info = list[position]
        holder.bindTo(info)


    }

    override fun getItemCount() = list.size

}