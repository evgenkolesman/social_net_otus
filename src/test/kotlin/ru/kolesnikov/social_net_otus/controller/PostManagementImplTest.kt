package ru.kolesnikov.social_net_otus.controller

import ContainerControllerMethods
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import ru.kolesnikov.social_net_otus.model.PostCreatePostRequest
import ru.kolesnikov.social_net_otus.testcontainers.AbstractIntegrationTestConfigurator


class PostManagementImplTest : AbstractIntegrationTestConfigurator() {
    val containerControllerMethods = ContainerControllerMethods(ObjectMapper())

    @Test
    fun postCreatePost() {
        containerControllerMethods
            .postRequestToController(
                "/post/create", PostCreatePostRequest("TEST TEXT"))

    }

    @Test
    fun postDeleteIdPut() {
    }

    @Test
    fun postUpdatePut() {
    }

    @Test
    fun postFeedGet() {
    }

    @Test
    fun postGetIdGet() {
    }
}