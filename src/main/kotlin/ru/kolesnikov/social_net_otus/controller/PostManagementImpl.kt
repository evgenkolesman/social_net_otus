package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.*
import ru.kolesnikov.social_net_otus.service.PostManagementService
import java.math.BigDecimal

@RestController
class PostManagementImpl(private val service: PostManagementService) : PostManagementApi {
    override fun postCreatePost(postRequest : PostText)
            : ResponseEntity<Post> = ResponseEntity.ok(service.postCreate(postRequest))

    override fun postDeleteIdPut(postId: PostId): ResponseEntity<Unit> = ResponseEntity.ok(service.postDeleteId(postId.id))

    override fun postUpdatePut(postUpdatePutRequest: Post)
            : ResponseEntity<Unit> = ResponseEntity.ok(service.postUpdate(postUpdatePutRequest))

    override fun postFeedGet(offset: BigDecimal, limit: BigDecimal): ResponseEntity<List<Post>> =
       ResponseEntity.ok(service.postFeedGet(offset, limit))


    override fun postGetIdGet(postId: PostId): ResponseEntity<Post> = ResponseEntity.ok(service.postGetById(postId.id))
}