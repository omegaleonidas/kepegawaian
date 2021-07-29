package sidiq.project.kepegawaian.model.login

data class DataUserRespon(
	val message: String? = null,
	val user: User? = null,
	val token: String? = null
)

data class User(
	val updatedAt: String? = null,
	val level: Int? = null,
	val name: String? = null,
	val createdAt: String? = null,
	val emailVerifiedAt: Any? = null,
	val id: Int? = null,
	val nip:Int? = null,
	val email: String? = null
)

