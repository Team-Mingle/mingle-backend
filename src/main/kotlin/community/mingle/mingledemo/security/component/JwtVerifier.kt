package community.mingle.mingledemo.security.component

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import community.mingle.mingledemo.dto.TokenContentDto
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.exception.auth.AuthenticateFailedException
import community.mingle.mingledemo.exception.auth.JwtTokenExpiredException
import community.mingle.mingledemo.infra.aws.service.SecretsManagerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class JwtVerifier(
    private val tokenAlgorithm: Algorithm,
    private val secretsManagerService: SecretsManagerService,
) {
    val tokenVerifier: JWTVerifier = JWT
        .require(tokenAlgorithm)
        .withClaimPresence("memberId")
        .withClaimPresence("memberRole")
        .build()

    fun verify(request: HttpServletRequest): TokenContentDto? {
        val bearerToken = request.getHeader("Authorization") ?: return null
        if (!bearerToken.startsWith("Bearer ")) return null

        return verifyToken(bearerToken)
    }

    fun verifyToken(bearerToken: String): TokenContentDto {
        try {
            val verification = with(secretsManagerService.getJwtDevToken()) {
                when (val token = bearerToken.substring(7)) {
                    mingleUser
                    -> TokenContentDto(
                        memberId = 1L,
                        memberRole = MemberRole.USER
                    )

                    mingleAdmin
                    -> TokenContentDto(
                        memberId = 2L,
                        memberRole = MemberRole.ADMIN
                    )

                    mingleKsa
                    -> TokenContentDto(
                        memberId = 3L,
                        memberRole = MemberRole.KSA
                    )

                    else -> verifyIssuedToken(token)
                }
            }
            return verification
        } catch (e: TokenExpiredException) {
            throw JwtTokenExpiredException()
        } catch (e: JWTVerificationException) {
            throw AuthenticateFailedException()
        }
    }

    fun verifyIssuedToken(
        token: String
    ): TokenContentDto {
        val verifiedJWT = tokenVerifier.verify(token)
        return TokenContentDto(
            memberId = verifiedJWT.getClaim("memberId").asLong(),
            memberRole = MemberRole.valueOf(verifiedJWT.getClaim("memberRole").asString()),
        )
    }
}