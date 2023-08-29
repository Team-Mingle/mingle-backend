package community.mingle.mingledemo.redis.configuration

import community.mingle.mingledemo.infra.aws.service.SecretsManagerService
import community.mingle.mingledemo.redis.RedisRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(
    includeFilters = [
        ComponentScan.Filter(RedisRepository::class)
    ],
)
class RedisConfiguration(
    secretsManagerService: SecretsManagerService,
) {

    private val redisConnectionSourceConfig = secretsManagerService.getRedisConnectionSourceConfig()
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(
        redisConnectionSourceConfig.host,
        redisConnectionSourceConfig.port
    )

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> = RedisTemplate<String, Any>()
        .apply { setConnectionFactory(redisConnectionFactory()) }

}