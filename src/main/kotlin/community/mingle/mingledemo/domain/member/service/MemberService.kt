package community.mingle.mingledemo.domain.member.service

import community.mingle.mingledemo.domain.auth.util.sha256
import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.member.entity.University
import community.mingle.mingledemo.domain.member.repository.MemberRepository
import community.mingle.mingledemo.domain.member.repository.MemberRepository.Companion.find
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import community.mingle.mingledemo.exception.DuplicatedEmailException
import community.mingle.mingledemo.exception.DuplicatedNicknameException
import community.mingle.mingledemo.exception.ReportedMemberSignUpException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun create(
        university: University,
        nickname: String,
        email: String,
        password: String,
        role: MemberRole,
        fcmToken: String,
    ): Member {
        return memberRepository.save(
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
    }

    fun getById(id: Long): Member = memberRepository.find(id)

    fun getByEmailOrNull(email: String): Member? {
        val encryptedEmail = email.sha256()
        return memberRepository.findByEmail(encryptedEmail)
    }

    fun checkExistenceByEmail(email: String) {
        if (isMemberExistedByEmail(email)) throw DuplicatedEmailException()
    }

    fun validateReportedStatus(email: String) {
        val member = getByEmailOrNull(email)
        if (member?.status == MemberStatus.REPORTED) {
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
