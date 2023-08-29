package community.mingle.mingledemo.dto

import community.mingle.mingledemo.enums.MemberRole

data class TokenContentDto(
    val memberId: Long,
    val memberRole: MemberRole
)