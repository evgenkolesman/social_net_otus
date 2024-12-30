package ru.kolesnikov.social_net_otus.testcontainers

import io.restassured.RestAssured
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import ru.kolesnikov.social_net_otus.SocialNetOtusApplication

@SpringBootTest(
    classes = [SocialNetOtusApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(initializers = [TestContainersInitializer.Initializer::class])
@ActiveProfiles("test")
abstract class AbstractIntegrationTestConfigurator {
    @LocalServerPort
    private val port = 0


    @BeforeEach
    fun portSet() {
        RestAssured.port = port
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun init() {
            System.setProperty("db.schema", "hr_outsource")
            TestContainersInitializer.container.start()
            RestAssured.baseURI = "http://localhost"

        }

        @JvmStatic
        @AfterAll
        fun clearData() {
            System.clearProperty("db.schema")
        }
    }
}