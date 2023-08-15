package community.mingle.mingledemo.datasource.configuration

import community.mingle.mingledemo.configuration.ReflectionConfiguration
import community.mingle.mingledemo.datasource.HikariDataSourceUtil
import community.mingle.mingledemo.dto.DataSourceConfig
import community.mingle.mingledemo.infra.aws.service.SecretsManagerService
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.hibernate5.SpringBeanContainer
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@EnableJpaRepositories(
    basePackages = [ReflectionConfiguration.BASE_PACKAGE],
)
@Configuration
class JpaRepositoriesConfiguration(
    private val secretsManagerService: SecretsManagerService,
    @Qualifier(ReflectionConfiguration.BeanName.BASE_PACKAGE)
    private val entityBasePackage: String,
    @Qualifier(community.mingle.mingledemo.configuration.ApplicationConfiguration.BeanName.PROFILE)
    private val profile: String,
    private val beanFactory: ConfigurableListableBeanFactory,
    @Qualifier(community.mingle.mingledemo.configuration.ApplicationConfiguration.BeanName.PROJECT_NAME)
    private val projectName: String,
) {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(): DataSource {
        //TODO AWS security config 설정하기
//        val dataSourceConfig =
//            secretsManagerService.getDataSourceConfig(profile)
        val dataSource = HikariDataSourceUtil.createHikariDataSource(
            poolName = projectName,
            dataSourceConfig = DataSourceConfig(
                username = "root",
                password = "mingle",
                engine = "mysql",
                host = "localhost",
                port = "9091",
                dbname = "mingle"
            )
        )

        return LazyConnectionDataSourceProxy(dataSource)
    }

    @Primary
    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource()
        em.setPackagesToScan(entityBasePackage)
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        em.setJpaPropertyMap(
            mapOf(
                AvailableSettings.DIALECT to "org.hibernate.dialect.MySQLDialect",
                AvailableSettings.PHYSICAL_NAMING_STRATEGY to "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
                AvailableSettings.BEAN_CONTAINER to SpringBeanContainer(beanFactory),
            ),
        )
        em.persistenceUnitName = projectName
        return em
    }

    @Primary
    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory().`object`
        transactionManager.persistenceUnitName = projectName
        return transactionManager
    }
}
