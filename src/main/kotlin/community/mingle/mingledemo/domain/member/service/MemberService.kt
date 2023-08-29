package community.mingle.mingledemo.domain.member.service

import community.mingle.mingledemo.domain.auth.util.sha256
import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.member.entity.University
import community.mingle.mingledemo.domain.member.repository.MemberRepository
import community.mingle.mingledemo.domain.member.util.MemberDtoUtil.toDto
import community.mingle.mingledemo.dto.member.MemberDto
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import community.mingle.mingledemo.exception.DuplicatedEmailException
import community.mingle.mingledemo.exception.DuplicatedNicknameException
import community.mingle.mingledemo.exception.ReportedMemberSignUpException
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun createMember(
        university: University,
        nickname: String,
        email: String,
        password: String,
        role: MemberRole,
        fcmToken: String,
    ): MemberDto {
        val member = memberRepository.save(
            Member(
                university = university,
                nickname = nickname,
                email = email.sha256(),
                password = password.sha256(),
                role = role,
                status = MemberStatus.ACTIVE,
                fcmToken = fcmToken
            )
        )
        return member.toDto()
    }

    fun getMemberByEmailOrNull(email: String): MemberDto? {
        val encryptedEmail = email.sha256()
        val member = memberRepository.findByEmail(encryptedEmail)
        return member?.toDto()
    }

    fun checkMemberExistedByEmail(email: String) {
        if (isMemberExistedByEmail(email)) throw DuplicatedEmailException()
    }

    fun validateReportedStatus(email: String) {
        val memberDto = getMemberByEmailOrNull(email)
        if (memberDto?.status == MemberStatus.REPORTED) {
            throw ReportedMemberSignUpException()
        }
    }

    fun checkDuplicatedNickName(nickname: String) {
        if (isDuplicatedNickName(nickname)) throw DuplicatedNicknameException()
    }

    private fun isMemberExistedByEmail(email: String): Boolean {
        val encryptedEmail = email.sha256()
        return memberRepository.existsMemberByEmail(encryptedEmail)
    }

    private fun isDuplicatedNickName(nickname: String) = memberRepository.existsMemberByNickname(nickname)


}
