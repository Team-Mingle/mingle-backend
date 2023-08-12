package community.mingle.mingledemo.common.dto

import java.io.Serializable

data class DataSourceConfig(
    val username: String,
    val password: String,
    val engine: String,
    val host: String,
    val port: String,
    val dbname: String,
) : Serializable
