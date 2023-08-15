package community.mingle.mingledemo.security.component

import community.mingle.mingledemo.dto.TokenDto
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class TokenParser {

    fun getTokenDto(): TokenDto =
        SecurityContextHolder.getContext().authentication.principal as TokenDto

    fun isAuthorized(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication != null && authentication.isAuthenticated
    }
}