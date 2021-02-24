package sidiq.project.kepegawaian.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.databinding.CardViewBinding
import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi

class AdapterApsensi: PagedListAdapter<DataItemAbsensi, RecyclerView.ViewHolder>(DataItemAbsensi().DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = apsensiHolder(CardViewBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

     override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is apsensiHolder) {
            holder.bindTo(getItem(position))
        }
    }

    class apsensiHolder(var binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: DataItemAbsensi?) {

            binding.tvTanggal1.text = item?.tanggal
           binding.tvJamMasuk.text = item?.jamMasuk
            binding.tvJamKeluar.text = item?.jamSelesai
           binding.tvAlamat.text = item?.alamat


        }

    }


}