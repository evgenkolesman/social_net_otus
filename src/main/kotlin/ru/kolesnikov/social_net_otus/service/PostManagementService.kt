package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.entity.PostManagementEntity
import ru.kolesnikov.social_net_otus.model.Post
import ru.kolesnikov.social_net_otus.model.PostText
import ru.kolesnikov.social_net_otus.repository.PostManagementRepository
import java.math.BigDecimal
import java.util.*

@Service
class PostManagementService(private val currentLoginProvider: CurrentLoginProvider,
    private val postManagementRepository: PostManagementRepository) {
    fun postCreate(postText: PostText): PostText {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        postManagementRepository.save(PostManagementEntity(
            login = currentLogin,
            text = postText.text
        ))
        return PostText(postText.text)
    }

    fun postDeleteId(id: String) {
        postManagementRepository.softDelete(UUID.fromString(id))
    }

    fun postUpdate(postUpdatePutRequest: Post) {
        postManagementRepository.updatePost(UUID.fromString(postUpdatePutRequest.id), postUpdatePutRequest.text)
    }

    fun postFeedGet(offset: BigDecimal, limit: BigDecimal):
            List<Post> = postManagementRepository.getWithLimitAndOffset(offset, limit)

    fun postGetById(id: String): Post {
        val post = postManagementRepository.findById(UUID.fromString(id))
        return if (post.isEmpty) Post("", "")
                else Post(post.get().id.toString(), post.get().text)
    }

}
