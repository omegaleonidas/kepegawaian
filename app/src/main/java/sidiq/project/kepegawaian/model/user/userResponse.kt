package sidiq.project.kepegawaian.model.user

data class userResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val email: String,
    val name: String,
    val nip: String,
    val nohp: String,
    val password: String
)