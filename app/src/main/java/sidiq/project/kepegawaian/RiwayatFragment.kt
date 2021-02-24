package sidiq.project.kepegawaian

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_riwayat.*
import sidiq.project.kepegawaian.ViewModel.AbsensiViewModel
import sidiq.project.kepegawaian.adapter.AdapterApsensi
import sidiq.project.kepegawaian.databinding.ActivityMainBinding
import sidiq.project.kepegawaian.databinding.FragmentRiwayatBinding
import sidiq.project.kepegawaian.model.absensi.DataItemAbsensi

class RiwayatFragment : Fragment() {

    private var binding: FragmentRiwayatBinding? = null
    private var viewModel: AbsensiViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        viewModel = ViewModelProviders.of(this).get(AbsensiViewModel::class.java)

        viewModel?.absensiMVVMModel?.observe(this, Observer {
            val adapter = AdapterApsensi()
            adapter.submitList(it)
            binding?.recylerRiwayat?.apply {
                binding?.recylerRiwayat?.setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                recyler_riwayat.adapter = adapter

            }


        })
    }


}

