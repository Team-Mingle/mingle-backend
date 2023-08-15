package community.mingle.mingledemo.security.bean

import community.mingle.mingledemo.dto.TokenDto
import community.mingle.mingledemo.security.component.JwtVerifier
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtVerifier: JwtVerifier,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val verifiedTokenDto = jwtVerifier.verify(request)

        authenticate(verifiedTokenDto)

        filterChain.doFilter(request, response)
    }

    private fun authenticate(
        tokenDto: TokenDto?,
    ): UsernamePasswordAuthenticationToken? {
        val authenticationToken = if (tokenDto != null)
            UsernamePasswordAuthenticationToken(
                tokenDto,
                listOf(SimpleGrantedAuthority("AUTHORIZED")),
            )
        else null

        SecurityContextHolder.getContext().authentication = authenticationToken

        return authenticationToken
    }
}