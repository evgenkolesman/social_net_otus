package ru.kolesnikov.social_net_otus.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import ru.kolesnikov.social_net_otus.entity.PostManagementEntity
import java.util.*

interface PostManagementRepository : CrudRepository<PostManagementEntity, UUID> {

    @Query(
        """
        UPDATE sno_posts 
        SET active = false
        WHERE id = :id
        RETURNING id
    """
    )
    fun softDelete(id: UUID): UUID?

    @Query(
        """
        UPDATE sno_posts 
        SET text_post = :text
        WHERE id = :id
        RETURNING id
    """
    )
    fun updatePost(id: UUID, text: String): UUID?


}
