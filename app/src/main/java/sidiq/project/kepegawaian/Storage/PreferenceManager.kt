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
        const val  NOHP  = "no_hp"
        const val IDABSENSI = "id_absensi"
        const val NAMAPEGAWAI = "nama_pegawai"
        const val LOGIN = "login"

    }

    fun saveLoginState(key:String, value: Boolean){
        editor.putBoolean(key,value)
        editor.apply()
    }

    fun isloged():Boolean{
        return sharePreference.getBoolean(LOGIN,false)
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

    fun saveIdAbsensi( key:String,value: Int){
        editor.putInt(key,value)
        editor.apply()
    }

    fun getIdAbsensi()  :Int?{
        return sharePreference.getInt(IDABSENSI,0)
    }


    fun saveNoHp( key:String,value: Long){
        editor.putLong(key,value)
        editor.apply()
    }

    fun getNoHp()  :Long?{
        return sharePreference.getLong(NOHP,0)
    }


    fun saveNama( key:String, value: String?){

        editor.putString(key, value)
        editor.apply()
    }

    fun getNama(): String? {

        return sharePreference.getString(NAMAPEGAWAI,null)
    }


}