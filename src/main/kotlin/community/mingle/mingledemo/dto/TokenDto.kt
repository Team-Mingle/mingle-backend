package community.mingle.mingledemo.dto

import community.mingle.mingledemo.enums.MemberRole

data class TokenDto (
    val memberId: Long,
    val memberRole: MemberRole
)