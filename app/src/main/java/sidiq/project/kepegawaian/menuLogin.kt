package sidiq.project.kepegawaian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_menu_login.*


class menuLogin : Fragment(), View.OnClickListener {

    lateinit var navControler: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)


        btnMenuLogin.setOnClickListener(this)
        btnMenuRegister.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnMenuLogin ->
                  navControler.navigate(R.id.action_menuLogin_to_loginFragment)
            R.id.btnMenuRegister ->
                  navControler.navigate(R.id.action_menuLogin_to_registerFragment)

        }
    }
}