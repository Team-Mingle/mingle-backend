package community.mingle.mingledemo.domain.member.service

import community.mingle.mingledemo.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun isMemberExistedByEmail(encryptedEmail: String) = memberRepository.existsMemberByEmail(encryptedEmail)
}