package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.entity.PostManagementEntity
import ru.kolesnikov.social_net_otus.model.PostCreatePostRequest
import ru.kolesnikov.social_net_otus.model.PostUpdatePutRequest
import ru.kolesnikov.social_net_otus.repository.PostManagementRepository
import java.util.UUID

@Service
class PostManagementService(private val currentLoginProvider: CurrentLoginProvider,
    private val postManagementRepository: PostManagementRepository) {
    fun postCreate(postCreatePostRequest: PostCreatePostRequest): String {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        postManagementRepository.save(PostManagementEntity(
            login = currentLogin,
            text = postCreatePostRequest.text
        ))
        return postCreatePostRequest.text
    }

    fun postDeleteId(id: String) {
        postManagementRepository.softDelete(UUID.fromString(id))
    }

    fun postUpdate(postUpdatePutRequest: PostUpdatePutRequest) {
        postManagementRepository.updatePost(UUID.fromString(postUpdatePutRequest.id), postUpdatePutRequest.text)
    }

}
