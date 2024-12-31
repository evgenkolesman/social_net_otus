package ru.kolesnikov.social_net_otus.controller

import ContainerControllerMethods
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.bean.override.mockito.MockitoBean
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.model.*
import ru.kolesnikov.social_net_otus.repository.UserRegisterRepository
import ru.kolesnikov.social_net_otus.service.RegistrationManagementService
import ru.kolesnikov.social_net_otus.testcontainers.AbstractIntegrationTestConfigurator

class RegistrationManagementImplTest : AbstractIntegrationTestConfigurator() {

    @MockitoBean
    lateinit var currentLoginProvider: CurrentLoginProvider

    @Autowired
    lateinit var regService: RegistrationManagementService

    @Autowired
    lateinit var registerRepository: UserRegisterRepository

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    val containerControllerMethods = ContainerControllerMethods(ObjectMapper())

    @BeforeEach
    fun setUp() {
        Mockito.`when`(currentLoginProvider.getCurrentLogin()).thenReturn("login")
    }

    @AfterEach
    fun clearData() {
        jdbcTemplate.update("DELETE from authorities", mutableMapOf<String, String>())
        jdbcTemplate.update("DELETE from users", mutableMapOf<String, String>())
        registerRepository.deleteAll()

    }


    @Test
    fun loginPost() {
        containerControllerMethods
            .postRequestToController(
                "/user/register",
                RegisterUserRequest("login_new", "password")
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(UserRegisterPost200Response::class.java)

        val loginResponse = containerControllerMethods
            .postRequestToController(
                "/login",
                LoginModel("login_new", "password")
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(LoginResponse::class.java)

        assertNotNull(loginResponse.token)
    }

    @Test
    fun userGetIdGet() {
        containerControllerMethods
            .postRequestToController(
                "/user/register",
                RegisterUserRequest("login_new", "password")
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(UserRegisterPost200Response::class.java)


        val user = containerControllerMethods
            .getRequestToController(
                "/user/get/login_new"
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(User::class.java)

        assertEquals(user, User("login_new"))
    }

    @Test
    fun userRegisterPost() {
        var userRegisterPost200Response = containerControllerMethods
            .postRequestToController(
                "/user/register",
                RegisterUserRequest("login_new", "password")
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(UserRegisterPost200Response::class.java)

        assertEquals(userRegisterPost200Response, UserRegisterPost200Response("login_new"))

    }

    @Test
    fun userSearchGet() {

        val body = RegisterUserRequest(
            userId = "login_new",
            password = "password",
            firstName = "first",
            secondName = "second"
        )
        var userRegisterPost200Response = containerControllerMethods
            .postRequestToController(
                "/user/register",
                body
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(UserRegisterPost200Response::class.java)

        val list = containerControllerMethods
            .getRequestToController(
                "/user/search?first_name=${body.firstName}&last_name=${body.secondName}"
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList("", User::class.java)

        assertEquals(
            list, listOf(
                User(
                    userId = "login_new",
                    firstName = "first",
                    secondName = "second"
                )
            )
        )


    }
}