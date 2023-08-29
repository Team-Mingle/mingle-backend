package community.mingle.mingledemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class MingleDemoApplication

fun main(args: Array<String>) {
    runApplication<MingleDemoApplication>(*args)
}
