package community.mingle.mingledemo.dto.auth

import community.mingle.mingledemo.dto.member.MemberDto

data class LoginDto(
    val memberDto: MemberDto,
    val accessToken: String,
    val refreshToken: String,
)
