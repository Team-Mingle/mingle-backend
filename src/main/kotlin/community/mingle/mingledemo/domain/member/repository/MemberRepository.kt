package community.mingle.mingledemo.domain.member.repository

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.exception.member.MemberNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email: String): Member?
    fun existsMemberByEmail(email: String): Boolean
    fun existsMemberByNickname(nickName: String): Boolean

    companion object {
        fun MemberRepository.find(id: Long) = findByIdOrNull(id)
            ?: throw MemberNotFoundException()
    }
}