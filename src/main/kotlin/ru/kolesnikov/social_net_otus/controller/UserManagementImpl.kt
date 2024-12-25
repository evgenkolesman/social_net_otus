package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.PostCreatePostRequest
import ru.kolesnikov.social_net_otus.model.PostUpdatePutRequest
import ru.kolesnikov.social_net_otus.service.UserManagementService

@RestController
class UserManagementImpl(private val service: UserManagementService) : UserManagementApi {
    override fun postCreatePost(postCreatePostRequest: PostCreatePostRequest?)
            : ResponseEntity<String> = ResponseEntity.ok(service.postCreate(postCreatePostRequest))

    override fun postDeleteIdPut(id: String): ResponseEntity<Unit> = ResponseEntity.ok(service.postDeleteId(id))


    override fun postUpdatePut(postUpdatePutRequest: PostUpdatePutRequest?)
            : ResponseEntity<Unit> = ResponseEntity.ok(service.postUpdate(postUpdatePutRequest))
}