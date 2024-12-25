package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.Post
import ru.kolesnikov.social_net_otus.model.User
import ru.kolesnikov.social_net_otus.service.SearchService
import java.math.BigDecimal

@RestController
class SearchManagementImpl(private val service: SearchService) : SearchApi {
    override fun postFeedGet(offset: BigDecimal, limit: BigDecimal): ResponseEntity<List<Post>> {
        return ResponseEntity.ok(service.postFeed(offset, limit))
    }

    override fun postGetIdGet(id: String): ResponseEntity<Post> {
        return ResponseEntity.ok(service.postGetById(id))
    }

    override fun userSearchGet(firstName: String, lastName: String): ResponseEntity<List<User>> {
        return ResponseEntity.ok(service.userSearchGet(firstName, lastName))

    }
}

