package sidiq.project.kepegawaian.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.ViewModel.CutiViewModel
import sidiq.project.kepegawaian.databinding.FragmentCutiBinding

class CutiFragment : Fragment() {

    var viewmodelCuti :CutiViewModel? = null
    var binding: FragmentCutiBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCutiBinding.inflate(inflater, container, false)

        var view = binding?.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodelCuti = ViewModelProviders.of(this).get(CutiViewModel::class.java)
        viewmodelCuti?.cutiMVVMmodel?.observe(this, Observer {



        })


    }


}