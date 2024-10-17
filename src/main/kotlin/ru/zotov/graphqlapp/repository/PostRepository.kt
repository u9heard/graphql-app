package ru.zotov.graphqlapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.zotov.graphqlapp.entity.Post

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findByUserIdIn(userIds: List<Long>): Set<Post>
}