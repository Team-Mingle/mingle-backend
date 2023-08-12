package community.mingle.mingledemo.common.infra.aws.service

import com.fasterxml.jackson.databind.ObjectMapper
import community.mingle.mingledemo.common.configuration.ApplicationConfiguration
import community.mingle.mingledemo.common.dto.DataSourceConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.ListSecretsResponse
import throwIfError

@Service
class SecretsManagerService(
    @Qualifier(ApplicationConfiguration.BeanName.PROJECT_NAME)
    private val projectName: String,
) {
    private val objectMapper =
        Jackson2ObjectMapperBuilder.json().build<ObjectMapper>()
    private val secretsManager = SecretsManagerClient.create()
    private val arns: Map<String, String> by lazy {
        secretsManager
            .listSecretsPaginator()
            .map(ListSecretsResponse::throwIfError)
            .flatMap(ListSecretsResponse::secretList)
            .associate { it.name() to it.arn() }
    }

    fun getJwtSecretKey(): String = getSecretValueString(
        "$projectName/jwt-secret-key",
    )

    fun getDataSourceConfig(profile: String): DataSourceConfig = getSecretValue(
        "$projectName/$profile/database/primary",
    )

    private fun getSecretValueString(name: String): String {
        val arn = arns[name]
            ?: throw IllegalArgumentException("Cannot find secret id: $name")

        return secretsManager
            .getSecretValue { it.secretId(arn) }
            .throwIfError()
            .secretString()
    }

    private inline fun <reified T : Any> getSecretValue(
        id: String,
    ): T {
        val value = getSecretValueString(id)
        return objectMapper.readValue(value, T::class.java)
    }
}
