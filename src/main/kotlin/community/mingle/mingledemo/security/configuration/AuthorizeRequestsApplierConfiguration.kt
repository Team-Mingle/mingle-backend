package community.mingle.mingledemo.security.configuration

import community.mingle.mingledemo.constants.MingleDemo
import community.mingle.mingledemo.security.AuthorizeRequestsApplier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class AuthorizeRequestsApplierConfiguration {

    @Profile(MingleDemo.Profile.LOCAL, MingleDemo.Profile.DEVELOP)
    @Bean(SecurityConfiguration.AUTHORIZE_REQUEST_APPLIER_BEAN_NAME)
    fun accessibleAuthorizeRequestsApplier(): AuthorizeRequestsApplier = {
        it.authorizeHttpRequests()
            .requestMatchers(
                *URLS_AUTHENTICATION,
                *URLS_DOCUMENT,
            )
            .permitAll()
            .requestMatchers(
                "/**"
            )
            .authenticated()
    }

    @Profile(MingleDemo.Profile.PRODUCTION)
    @Bean(SecurityConfiguration.AUTHORIZE_REQUEST_APPLIER_BEAN_NAME)
    fun restrictedAuthorizeRequestsApplier(): AuthorizeRequestsApplier = {
        it.authorizeHttpRequests()
            .requestMatchers(
                *URLS_AUTHENTICATION,
            )
            .permitAll()
            .requestMatchers(
                "/**"
            )
            .authenticated()
    }

    companion object {
        private val URLS_AUTHENTICATION = arrayOf(
            "/auth/**",
            "/country"
        )
        private val URLS_DOCUMENT = arrayOf(
            "/swagger-ui/**",
            "/v3/api-docs/**",
        )
    }
}
