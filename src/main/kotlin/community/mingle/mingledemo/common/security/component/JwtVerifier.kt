package community.mingle.mingledemo.common.security.component

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import community.mingle.mingledemo.common.dto.TokenDto
import community.mingle.mingledemo.common.enums.MemberRole
import community.mingle.mingledemo.common.exception.AuthenticateFailedException
import community.mingle.mingledemo.common.exception.JwtTokenExpiredException
import community.mingle.mingledemo.common.infra.aws.service.SecretsManagerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class JwtVerifier(
    private val tokenAlgorithm: Algorithm,
    private val secretsManagerService: SecretsManagerService,
){
    val tokenVerifier: JWTVerifier = JWT
        .require(tokenAlgorithm)
        .withClaimPresence("memberId")
        .withClaimPresence("memberRole")
        .build()

    fun verify(request: HttpServletRequest): TokenDto? {
        val bearerToken = request.getHeader("Authorization") ?: return null
        if (!bearerToken.startsWith("Bearer ")) return null

        return verifyToken(bearerToken)
    }

    fun verifyToken(bearerToken: String): TokenDto {
        try {
            val verification = with(secretsManagerService.getJwtDevToken()) {
                when (val token = bearerToken.substring(7)) {
                    mingleUser
                    -> TokenDto(
                        memberId = 1L,
                        memberRole = MemberRole.USER
                    )

                    mingleAdmin
                    -> TokenDto(
                        memberId = 2L,
                        memberRole = MemberRole.ADMIN
                    )

                    mingleKsa
                    -> TokenDto(
                        memberId = 3L,
                        memberRole = MemberRole.KSA
                    )

                    else -> {
                        val verifiedJWT = tokenVerifier.verify(token)
                        TokenDto(
                            memberId = verifiedJWT.getClaim("memberId").asLong(),
                            memberRole = MemberRole.valueOf(verifiedJWT.getClaim("memberRole").asString()),
                        )
                    }
                }
            }
            return verification
        } catch (e: TokenExpiredException) {
            throw JwtTokenExpiredException()
        } catch (e: JWTVerificationException) {
            throw AuthenticateFailedException()
        }
    }
}