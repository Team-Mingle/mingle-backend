package community.mingle.mingledemo.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    private val securityScheme = SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .`in`(SecurityScheme.In.HEADER)
        .name("Authorization")
    private val securityRequirementName = "bearerAuth"

    @Bean
    fun openApi(): OpenAPI {

        return OpenAPI()
            .servers(listOf(Server().apply { url = "/" }))
            .security(
                listOf(
                    SecurityRequirement()
                        .addList(securityRequirementName),
                ),
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        securityRequirementName,
                        securityScheme,
                    ),
            )
            .info(
                Info()
                    .title("mingle-server demo")
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("mingle-server demo"),
            )
    }
}
