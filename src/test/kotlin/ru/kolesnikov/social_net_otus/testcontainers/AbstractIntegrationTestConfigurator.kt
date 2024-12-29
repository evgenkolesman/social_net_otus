package ru.kolesnikov.social_net_otus.testcontainers

import io.restassured.RestAssured
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration
import ru.kolesnikov.social_net_otus.SocialNetOtusApplication
import ru.kolesnikov.social_net_otus.controller.RegistrationManagementImpl
import ru.kolesnikov.social_net_otus.model.LoginModel
import ru.kolesnikov.social_net_otus.model.RegisterUserRequest

@SpringBootTest(
    classes = [SocialNetOtusApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(initializers = [TestContainersInitializer.Initializer::class])
//@AutoConfigureMockMvc(addFilters = false)
abstract class AbstractIntegrationTestConfigurator {
    @LocalServerPort
    private val port = 0

    @Autowired
    lateinit var registrationManagementImpl: RegistrationManagementImpl

    lateinit var token: String

    @BeforeAll
    fun authorizedOnce() {
        registrationManagementImpl.userRegisterPost(
            RegisterUserRequest(
                userId = "test_user",
                password = "password"
            )

        )

        var loginPost = registrationManagementImpl.loginPost(
            LoginModel(
                id = "test_user",
                password = "password"
            )
        )
        token = loginPost.body?.token.toString()
    }


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