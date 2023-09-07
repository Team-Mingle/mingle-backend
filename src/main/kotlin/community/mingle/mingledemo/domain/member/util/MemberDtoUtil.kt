package community.mingle.mingledemo.domain.member.util

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.member.util.UniversityDtoUtil.toDto
import community.mingle.mingledemo.dto.member.MemberDto

object MemberDtoUtil {

    fun Member.toDto(): MemberDto =
        MemberDto(
            id = id,
            universityDto = university.toDto(),
            nickname = nickname,
            email = email,
            password = password,
            status = status,
            role = role,
            agreedAt = agreedAt,
            deletedAt = deletedAt,
            fcmToken = fcmToken
        )

    fun List<Member>.toDtos(): List<MemberDto> =
        this.map { it.toDto() }
}