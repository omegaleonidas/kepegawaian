package sidiq.project.kepegawaian.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*
import sidiq.project.kepegawaian.R

class RegisterFragment : Fragment(), View.OnClickListener {

    lateinit var navControler: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_register, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener(this)





    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnRegister ->  navControler.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}