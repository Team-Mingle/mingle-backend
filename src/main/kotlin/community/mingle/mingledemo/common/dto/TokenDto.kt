package community.mingle.mingledemo.common.dto

import community.mingle.mingledemo.common.enums.MemberRole

data class TokenDto (
    val memberId: Long,
    val memberRole: MemberRole
)