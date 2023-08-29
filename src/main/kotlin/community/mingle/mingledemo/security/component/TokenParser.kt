package community.mingle.mingledemo.security.component

import community.mingle.mingledemo.dto.TokenContentDto
import community.mingle.mingledemo.enums.MemberRole
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class TokenParser {

    fun getTokenDto(): TokenContentDto =
        SecurityContextHolder.getContext().authentication.principal as TokenContentDto

    fun isAuthorized(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication != null && authentication.isAuthenticated
    }

    fun getMemberId(): Long = getTokenDto().memberId
    fun getMemberRole(): MemberRole = getTokenDto().memberRole
}