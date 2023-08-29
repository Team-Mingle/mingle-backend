package community.mingle.mingledemo.domain.auth.controller.response

import java.io.Serializable

data class SignUpOrLoginResponse(
    val memberId: Long,
    val email: String,
    val nickname: String,
    val universityName: String,
    val accessToken: String,
    val refreshToken: String,
) : Serializable