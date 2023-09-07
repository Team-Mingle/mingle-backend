package community.mingle.mingledemo.dto.member

import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import java.io.Serializable
import java.time.LocalDateTime

data class MemberDto(
    val id: Long?,
    var universityDto: UniversityDto,
    var nickname: String,
    var email: String,
    var password: String,
    var status: MemberStatus,
    var role: MemberRole,
    var agreedAt: LocalDateTime?,
    var deletedAt: LocalDateTime?,
    var fcmToken: String?,
) : Serializable
