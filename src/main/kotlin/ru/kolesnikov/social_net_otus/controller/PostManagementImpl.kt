package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.Post
import ru.kolesnikov.social_net_otus.model.PostCreatePostRequest
import ru.kolesnikov.social_net_otus.model.PostUpdatePutRequest
import ru.kolesnikov.social_net_otus.service.PostManagementService
import java.math.BigDecimal

@RestController
class PostManagementImpl(private val service: PostManagementService) : PostManagementApi {
    override fun postCreatePost(postCreatePostRequest: PostCreatePostRequest?)
            : ResponseEntity<String> = ResponseEntity.ok(service.postCreate(postCreatePostRequest!!))

    override fun postDeleteIdPut(id: String): ResponseEntity<Unit> = ResponseEntity.ok(service.postDeleteId(id))

    override fun postUpdatePut(postUpdatePutRequest: PostUpdatePutRequest?)
            : ResponseEntity<Unit> = ResponseEntity.ok(service.postUpdate(postUpdatePutRequest!!))

    override fun postFeedGet(offset: BigDecimal, limit: BigDecimal): ResponseEntity<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun postGetIdGet(id: String): ResponseEntity<Post> {
        TODO("Not yet implemented")
    }
}