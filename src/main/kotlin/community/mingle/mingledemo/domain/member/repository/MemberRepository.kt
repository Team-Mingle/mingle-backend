package community.mingle.mingledemo.domain.member.repository

import community.mingle.mingledemo.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsMemberByEmail(email: String): Boolean
}