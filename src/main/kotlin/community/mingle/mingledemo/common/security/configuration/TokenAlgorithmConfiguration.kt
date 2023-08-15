package community.mingle.mingledemo.common.security.configuration

import com.auth0.jwt.algorithms.Algorithm
import community.mingle.mingledemo.common.infra.aws.service.SecretsManagerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenAlgorithmConfiguration(
    private val secretsManagerService: SecretsManagerService,
) {
    @Bean
    fun tokenAlgorithm(): Algorithm {
        val secret = secretsManagerService.getJwtSecretKey()
        return Algorithm.HMAC256(secret)
    }
}
