package community.mingle.mingledemo.domain.auth.controller.response

data class ReissueTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
