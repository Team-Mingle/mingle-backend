package community.mingle.mingledemo.security.component

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import community.mingle.mingledemo.enums.MemberRole
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtHandler(
    private val tokenAlgorithm: Algorithm,
) {

    fun createAccessToken(
        memberId: Long,
        memberRole: MemberRole
    ) = JWT.create()
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
}