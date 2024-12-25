package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.model.Post
import ru.kolesnikov.social_net_otus.model.User
import java.math.BigDecimal

@Service
class SearchService {
    fun postFeed(offset: BigDecimal, limit: BigDecimal): List<Post> {
        TODO("Not yet implemented")
        return listOf()
    }

    fun postGetById(id: String): Post? {
        return null
    }

    fun userSearchGet(firstName: String, lastName: String): List<User>? {
        return listOf()
    }

}
