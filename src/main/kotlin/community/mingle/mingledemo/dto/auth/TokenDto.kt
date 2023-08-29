package community.mingle.mingledemo.dto.auth

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
)
