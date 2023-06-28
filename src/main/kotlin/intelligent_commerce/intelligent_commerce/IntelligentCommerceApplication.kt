package intelligent_commerce.intelligent_commerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.filter.HiddenHttpMethodFilter

@SpringBootApplication
@EnableJpaAuditing
class IntelligentCommerceApplication

fun main(args: Array<String>) {
	runApplication<IntelligentCommerceApplication>(*args)

	@Bean
	fun hiddenMethodFilter(): HiddenHttpMethodFilter = HiddenHttpMethodFilter()
}
