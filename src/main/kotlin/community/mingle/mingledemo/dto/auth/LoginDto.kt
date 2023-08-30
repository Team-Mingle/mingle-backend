package community.mingle.mingledemo.dto.auth

import community.mingle.mingledemo.domain.member.entity.Member

data class LoginDto(
    val member: Member,
    val accessToken: String,
    val refreshToken: String,
)
