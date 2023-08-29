package community.mingle.mingledemo.security.configuration

import com.auth0.jwt.algorithms.Algorithm
import community.mingle.mingledemo.infra.aws.service.SecretsManagerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenAlgorithmConfiguration(
    private val secretsManagerService: SecretsManagerService,
) {
    @Bean
    fun tokenAlgorithm(): Algorithm {
        val secret = secretsManagerService.getJwtSecretKey()
        //TODO aws secret manager 설정 후 jwt key 받아오기
        return Algorithm.HMAC256("secret")
    }
}
