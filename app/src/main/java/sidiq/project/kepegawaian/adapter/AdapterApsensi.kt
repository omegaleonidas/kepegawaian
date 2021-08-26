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
import sidiq.project.kepegawaian.model.absensi.Absensi

import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi

class AdapterApsensi(private val data: List<Absensi>) :
    RecyclerView.Adapter<AdapterApsensi.apsensiHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = apsensiHolder(
        CardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    class apsensiHolder(var binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: Absensi?) {

            binding.tvTanggal1.text = item?.tanggal
            binding.tvJamMasuk.text = item?.jam_masuk
            binding.tvJamMasukKeterangan.text = item?.keterangan
            binding.tvJamKeluar.text = item?.jam_selesai
            binding.tvJamKeluarketerangan.text = item?.keterangan_sore
            binding.tvAlamat.text = item?.alamat
            binding.tvAlamatSore.text = item?.alamat_sore


        }

    }

    override fun onBindViewHolder(holder: AdapterApsensi.apsensiHolder, position: Int) {
        val info = data[position]
        holder.bindTo(info)
    }

    override fun getItemCount()= data.size




}