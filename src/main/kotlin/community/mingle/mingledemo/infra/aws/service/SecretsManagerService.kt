package community.mingle.mingledemo.infra.aws.service

import com.fasterxml.jackson.databind.ObjectMapper
import community.mingle.mingledemo.dto.DataSourceConfig
import community.mingle.mingledemo.dto.DevToken
import community.mingle.mingledemo.dto.RedisConnectionSourceConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.ListSecretsResponse
import throwIfError

@Service
class SecretsManagerService(
    @Qualifier(community.mingle.mingledemo.configuration.ApplicationConfiguration.BeanName.PROJECT_NAME)
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

    fun getJwtSecretKey(): String = "jwt-secret-key"
        //TODO aws secret manager 설정
//        getSecretValueString(
//        "$projectName/jwt-secret-key",
//    )

    fun getJwtDevToken(): DevToken =  DevToken(
        mingleUser = "mingleUser",
        mingleAdmin = "mingleAdmin",
        mingleKsa = "mingleKsa"
    )
        //TODO aws secret manager 설정
//        getSecretValue(
//        "$projectName/jwt-dev-token",
//    )

    fun getDataSourceConfig(profile: String): DataSourceConfig = getSecretValue(
        "$projectName/$profile/database/primary",
    )

    fun getRedisConnectionSourceConfig(): RedisConnectionSourceConfig = RedisConnectionSourceConfig(
        host = "localhost",
        port = 9092,
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
