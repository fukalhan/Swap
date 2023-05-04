package cz.cvut.fukalhan.swap.auth.model

data class SignUpCredentials(
    val email: String,
    val username: String,
    val password: String,
    val fcmToken: String
)
