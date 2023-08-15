package community.mingle.mingledemo.security.configuration

import community.mingle.mingledemo.security.AuthorizeRequestsApplier
import community.mingle.mingledemo.security.bean.JwtAccessDeniedHandler
import community.mingle.mingledemo.security.bean.JwtAuthenticationEntryPoint
import community.mingle.mingledemo.security.bean.JwtFilter
import community.mingle.mingledemo.security.component.CustomPasswordEncoder
import community.mingle.mingledemo.security.component.JwtVerifier
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.AdviceMode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    securedEnabled = true,
    prePostEnabled = true,
    mode = AdviceMode.PROXY,
)
class SecurityConfiguration(
    @Qualifier(AUTHORIZE_REQUEST_APPLIER_BEAN_NAME)
    private val authorizeRequestsApplier: AuthorizeRequestsApplier,
    private val jwtVerifier: JwtVerifier,
) {
    @Bean
    fun passwordEncoder() = CustomPasswordEncoder()

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint = JwtAuthenticationEntryPoint()

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler = JwtAccessDeniedHandler()

    @Bean
    fun jwtFilter(): JwtFilter = JwtFilter(jwtVerifier)

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors {}
            .csrf {
                it.disable()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint())
                it.accessDeniedHandler(accessDeniedHandler())
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.shouldFilterAllDispatcherTypes(false)
            }
            .apply(authorizeRequestsApplier)
            .addFilterBefore(
                jwtFilter(),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .build()
    }

    companion object {
        const val AUTHORIZE_REQUEST_APPLIER_BEAN_NAME = "authorizeRequestsApplier"
    }
}
