package community.mingle.mingledemo.common.configuration

import community.mingle.mingledemo.common.constants.MingleDemo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class ApplicationConfiguration(
    private val environment: Environment,
) {
    @Bean(BeanName.PROJECT_NAME)
    fun projectName(): String = MingleDemo.PROJECT_NAME

    @Bean(BeanName.PROFILE)
    fun profile(): String = environment.activeProfiles.first()

    @Bean(BeanName.IS_LOCAL)
    fun isLocal(): Boolean = profile() == MingleDemo.Profile.LOCAL

    object BeanName {
        const val PROFILE = "profile"
        const val PROJECT_NAME = "projectName"
        const val IS_LOCAL = "isLocal"
    }
}
