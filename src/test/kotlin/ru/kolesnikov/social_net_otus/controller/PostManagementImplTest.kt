package ru.kolesnikov.social_net_otus.controller

import ContainerControllerMethods
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.test.context.support.WithMockUser
import ru.kolesnikov.social_net_otus.model.Post
import ru.kolesnikov.social_net_otus.model.PostText
import ru.kolesnikov.social_net_otus.repository.PostManagementRepository
import ru.kolesnikov.social_net_otus.testcontainers.AbstractIntegrationTestConfigurator
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class PostManagementImplTest : AbstractIntegrationTestConfigurator() {
    val containerControllerMethods = ContainerControllerMethods(ObjectMapper())

    @Autowired
    lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    @Autowired
    lateinit var postManagementRepository: PostManagementRepository

    lateinit var id: UUID


    @BeforeEach
    fun prepareDB() {

        id = namedParameterJdbcTemplate.queryForObject(
            "INSERT INTO sno_posts (text_post, login ) VAlUES ('text', 'login') RETURNING id",
            mutableMapOf<String, String>(),
            UUID::class.java
        )!!
    }


    @AfterEach
    fun clearDB() {
        namedParameterJdbcTemplate.update(
            "DELETE FROM sno_posts ",
            mutableMapOf<String, String>()
        )

    }


    @Test
    @WithMockUser(username = "test_user", password = "password")
    fun postCreatePost() {
        val body = PostText("TEST TEXT")
        val value = containerControllerMethods
            .postRequestToController(
                "/post/create", body
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(PostText::class.java)
        assertEquals(value, body)
    }

    @Test
    fun postDeleteIdPut() {
        containerControllerMethods
            .putRequestToController(
                "/post/delete/${id}", ""
            )
            .and()
            .assertThat()
            .statusCode(200)

        assertFalse { postManagementRepository.findById(id).get().active }
    }

    @Test
    fun postUpdatePut() {
        containerControllerMethods
            .putRequestToController(
                "/post/update", Post(id.toString(), "new text")
            )
            .and()
            .assertThat()
            .statusCode(200)
        val findById = postManagementRepository.findById(id)
        assertTrue { findById.isPresent }
        assertEquals(Post(findById.get().id.toString(), findById.get().text), Post(id.toString(), "new text"))

    }

    @Test
    fun postFeedGet() {
        val post = containerControllerMethods
            .getRequestToController(
                "/post/feed"
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .response().jsonPath().getList("", Post::class.java)

        assertEquals(post, listOf(Post(id.toString(), "text")))
    }

    @Test
    fun postGetIdGet() {
        val post = containerControllerMethods
            .getRequestToController(
                "/post/get/${id}"
            )
            .and()
            .assertThat()
            .statusCode(200)
            .extract()
            .`as`(Post::class.java)

        assertEquals(post, Post(id.toString(), "text"))
    }
}