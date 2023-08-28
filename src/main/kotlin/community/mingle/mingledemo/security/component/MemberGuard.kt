package community.mingle.mingledemo.security.component

import community.mingle.mingledemo.enums.MemberRole
import org.springframework.stereotype.Component

@Component
class MemberGuard(
    private val tokenParser: TokenParser,
) {
    fun check() = tokenParser.isAuthorized()

    fun hasAuthority(vararg memberRole: MemberRole): Boolean {
        val tokenDto = tokenParser.getTokenDto()
        return memberRole.contains(tokenDto.memberRole)
    }
}