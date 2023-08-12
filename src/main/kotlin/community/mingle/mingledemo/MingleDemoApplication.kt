package community.mingle.mingledemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MingleDemoApplication

fun main(args: Array<String>) {
    runApplication<MingleDemoApplication>(*args)
}
