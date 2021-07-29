package sidiq.project.kepegawaian.Storage

import android.content.Context
import android.content.SharedPreferences



class PreferenceManager (private  val context: Context) {



    private  val sharePreference : SharedPreferences = context.getSharedPreferences("TAG",Context.MODE_PRIVATE)

    private  val editor : SharedPreferences.Editor = sharePreference.edit()

    companion object {
        const val KEY_TOKEN = "key_token"
        const val ID = "id_pegawai"
        const val NIP = "nip"
    }



    fun saveToken( key:String, value: String?){

        editor.putString(key, value)
        editor.apply()
    }

    fun getToken(): String? {

        return sharePreference.getString(KEY_TOKEN,null)
    }

    fun saveNip( key:String,value: Int){
        editor.putInt(key,value)
        editor.apply()
    }

    fun getNip()  :Int?{
        return sharePreference.getInt(NIP,0)
    }

    fun saveId( key:String,value: Int){
        editor.putInt(key,value)
        editor.apply()
    }

    fun getId()  :Int?{
        return sharePreference.getInt(ID,0)
    }
}