package sidiq.project.kepegawaian.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import sidiq.project.kepegawaian.ViewModel.RegisterViewModel
import sidiq.project.kepegawaian.R
import sidiq.project.kepegawaian.model.DataItem

class LoginFragment : Fragment(), View.OnClickListener {

    lateinit var navControler: NavController
    private var viewModel: RegisterViewModel? = null
    private var item: DataItem? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)


        //   viewModel?.getData()
        viewModel?.registerMVVMModel?.observe(this, Observer {



        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)

        btnLogin.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin ->
                navControler.navigate(R.id.action_loginFragment_to_verivikasiOTP)


        }
    }


}