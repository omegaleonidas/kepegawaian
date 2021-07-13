package sidiq.project.kepegawaian.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataUserRespon1 {
    @SerializedName("user")
    @Expose
    var user: User? = null

    class User {

//        @SerializedName("email")
//        @Expose
//        var email: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

//        @SerializedName("token")
//        @Expose
//        var token: String? = null

    }

}