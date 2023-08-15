package community.mingle.mingledemo.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import community.mingle.mingledemo.datasource.HikariDataSourceUtil.Driver.MYSQL
import community.mingle.mingledemo.dto.DataSourceConfig

object HikariDataSourceUtil {

    fun createHikariDataSource(
        poolName: String,
        dataSourceConfig: DataSourceConfig,
    ): HikariDataSource {
        val hikariConfig = HikariConfig().apply {
            this.poolName = poolName
            this.driverClassName = MYSQL
            this.username = dataSourceConfig.username
            this.password = dataSourceConfig.password
            this.jdbcUrl = "jdbc:mysql://${dataSourceConfig.host}:${dataSourceConfig.port}/${dataSourceConfig.dbname}"
        }
        return HikariDataSource(hikariConfig)
    }

    object Driver {
        const val MYSQL = "com.mysql.cj.jdbc.Driver"
    }
}