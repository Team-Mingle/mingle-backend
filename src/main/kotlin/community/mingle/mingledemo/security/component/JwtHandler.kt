package community.mingle.mingledemo.security.component

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import community.mingle.mingledemo.domain.auth.entity.RefreshToken
import community.mingle.mingledemo.domain.auth.repository.RefreshTokenRedisRepository
import community.mingle.mingledemo.domain.auth.repository.RefreshTokenRedisRepository.Companion.find
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.exception.AuthenticateFailedException
import community.mingle.mingledemo.exception.JwtTokenExpiredException
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtHandler(
    private val tokenAlgorithm: Algorithm,
    private val refreshTokenRedisRepository: RefreshTokenRedisRepository,
    private val jwtVerifier: JwtVerifier,
) {
    val tokenVerifier: JWTVerifier = JWT
        .require(tokenAlgorithm)
        .withClaimPresence("memberId")
        .withClaimPresence("memberRole")
        .build()

    fun createAccessToken(
        memberId: Long,
        memberRole: MemberRole
    ): String = JWT.create()
        .withClaim("memberId", memberId)
        .withClaim("memberRole", memberRole.toString())
        .withExpiresAt(
            Date.from(
                LocalDateTime
                    .now()
                    .plusMinutes(30)
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toInstant()
            )
        )
        .sign(tokenAlgorithm)

    fun createRefreshToken(
        memberId: Long,
        memberRole: MemberRole
    ): String {
        val refreshToken = JWT.create()
            .withClaim("memberId", memberId)
            .withClaim("memberRole", memberRole.toString())
            .withExpiresAt(
                Date.from(
                    LocalDateTime
                        .now()
                        .plusYears(1L)
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant()
                )
            )
            .sign(tokenAlgorithm)

        refreshTokenRedisRepository.save(
            RefreshToken(refreshToken)
        )

        return refreshToken
    }

    fun verifyRefreshToken(
        unverifiedRefreshToken: String,
    ): RefreshToken {
        try {
            tokenVerifier.verify(unverifiedRefreshToken)
        } catch (e: TokenExpiredException) {
            throw JwtTokenExpiredException()
        } catch (e: JWTVerificationException) {
            throw AuthenticateFailedException()
        }
        val verifiedRefreshToken = refreshTokenRedisRepository.find(unverifiedRefreshToken)
        refreshTokenRedisRepository.delete(verifiedRefreshToken)
        return verifiedRefreshToken
    }
}