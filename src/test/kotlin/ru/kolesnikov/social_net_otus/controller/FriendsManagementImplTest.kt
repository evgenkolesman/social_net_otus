package ru.kolesnikov.social_net_otus.controller

import ContainerControllerMethods
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.bean.override.mockito.MockitoBean
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.entity.FriendsManagementEntity
import ru.kolesnikov.social_net_otus.repository.FriendsManagementRepository
import ru.kolesnikov.social_net_otus.testcontainers.AbstractIntegrationTestConfigurator
import java.sql.PreparedStatement

class FriendsManagementImplTest : AbstractIntegrationTestConfigurator() {

    @MockitoBean
    lateinit var currentLoginProvider: CurrentLoginProvider

    @Autowired
    lateinit var friendsManagementRepository: FriendsManagementRepository

    @Autowired
    lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    val containerControllerMethods = ContainerControllerMethods(ObjectMapper())

    @BeforeEach
    fun setUp() {
        Mockito.`when`(currentLoginProvider.getCurrentLogin()).thenReturn("login")

        friendsManagementRepository.save(
            FriendsManagementEntity(firstLogin = "login", secondLogin = "other_login")
        )
    }

    @AfterEach
    fun clearDb() {
        friendsManagementRepository.deleteAll()

        namedParameterJdbcTemplate.execute(
            """
                        SELECT pg_catalog.setval('"sno_friends_id_seq"', 1, false)""",
            PreparedStatement::execute
        )
    }

    @Test
    fun friendDeleteUserIdPut() {

        containerControllerMethods
            .putRequestToController(
                "/friend/delete/login", ""
            )
            .and()
            .assertThat()
            .statusCode(200)

        kotlin.test.assertFalse { friendsManagementRepository.findAll().toList().get(0).active }
    }

    @Test
    fun friendSetUserIdPut() {

        containerControllerMethods
            .putRequestToController(
                "/friend/set/friend", ""
            )
            .and()
            .assertThat()
            .statusCode(200)

        kotlin.test.assertTrue { friendsManagementRepository.findAll().toList().size == 2 }
        kotlin.test.assertTrue { friendsManagementRepository.findAll().toList().get(1).active }
        kotlin.test.assertEquals(friendsManagementRepository.findAll().toList().get(1).secondLogin, "friend")

    }
}