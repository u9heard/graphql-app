package ru.zotov.graphqlapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import ru.zotov.graphqlapp.entity.Comment

@Repository
interface CommentRepository : JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    fun findByPostIdIn(userIds: List<Long>): List<Comment>
}