package community.mingle.mingledemo.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refreshToken", timeToLive = RefreshToken.TTL_SECONDS)
class RefreshToken (
    @Id
    val refreshToken: String,
){
    companion object {
        const val TTL_SECONDS = 31536000L
    }
}