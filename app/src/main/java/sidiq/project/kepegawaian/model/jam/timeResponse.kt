package sidiq.project.kepegawaian.model.jam

data class timeResponse(
    val `data`: Data
)

data class Data(
    val Hour: Int,
    val date: String,
    val jam: String,
    val minute:Int,
    val date_time:String
)