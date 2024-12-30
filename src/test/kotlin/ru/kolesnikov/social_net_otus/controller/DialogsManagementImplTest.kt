package ru.kolesnikov.social_net_otus.controller

import ContainerControllerMethods
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.bean.override.mockito.MockitoBean
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.entity.DialogsManagementEntity
import ru.kolesnikov.social_net_otus.model.DialogMessage
import ru.kolesnikov.social_net_otus.model.DialogMessageText
import ru.kolesnikov.social_net_otus.repository.DialogsManagementRepository
import ru.kolesnikov.social_net_otus.service.DialogsManagementService
import ru.kolesnikov.social_net_otus.testcontainers.AbstractIntegrationTestConfigurator
import kotlin.test.assertEquals

class DialogsManagementImplTest : AbstractIntegrationTestConfigurator() {

    @MockitoBean
    lateinit var currentLoginProvider: CurrentLoginProvider

    @Autowired
    lateinit var dialogsManagementService: DialogsManagementService

    @Autowired
    lateinit var dialogsManagementRepository: DialogsManagementRepository

    val restMethods = ContainerControllerMethods(ObjectMapper())
    lateinit var savedMessage: DialogsManagementEntity

    @BeforeEach
    fun setUp() {
        Mockito.`when`(currentLoginProvider.getCurrentLogin()).thenReturn("login")
        savedMessage = dialogsManagementRepository
            .save(
                DialogsManagementEntity(from = "another_login", to = "login", text = "Hi, man!")
            )


    }

    @AfterEach
    fun cleanDb() {
        dialogsManagementRepository.deleteAll()
    }


    @Test
    fun dialogUserIdListGet() {
        val list = restMethods.getRequestToController("/dialog/login/list")
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .response()
            .jsonPath()
            .getList("", DialogMessage::class.java)

        assertEquals(mutableListOf(DialogMessage(savedMessage.from, savedMessage.to, savedMessage.text)), list)


    }

    @Test
    fun dialogUserIdSendPost() {
        val text = "Hi man, another_login"
        restMethods.postRequestToController("/dialog/another_login/send",
            DialogMessageText(text))
            .and()
            .assertThat()
            .statusCode(200)

        val findAll = dialogsManagementRepository.findAll().toList()

        assertEquals(findAll.size, 2)
        assertEquals(findAll.get(1).to, "login")
        assertEquals(findAll.get(1).from, "another_login")
        assertEquals(findAll.get(1).text, text)
    }
}