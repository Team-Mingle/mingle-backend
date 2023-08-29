package community.mingle.mingledemo.security.component

import community.mingle.mingledemo.dto.TokenContentDto
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
}