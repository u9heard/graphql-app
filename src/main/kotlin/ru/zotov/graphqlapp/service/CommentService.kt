package ru.zotov.graphqlapp.service

import org.springframework.stereotype.Service
import ru.zotov.graphqlapp.dto.CommentRq
import ru.zotov.graphqlapp.dto.CommentRs
import ru.zotov.graphqlapp.exception.PostNotFoundException
import ru.zotov.graphqlapp.exception.UserNotFoundException
import ru.zotov.graphqlapp.mapper.CommentMapper
import ru.zotov.graphqlapp.repository.CommentRepository
import ru.zotov.graphqlapp.repository.PostRepository
import ru.zotov.graphqlapp.repository.UserRepository

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val commentMapper: CommentMapper
) {

    fun findCommentsForPosts(postIds: List<Long>): List<CommentRs> {
        return commentRepository.findByPostIdIn(postIds)
            .map { commentMapper.map(it) }
    }

    fun createComment(commentRq: CommentRq): CommentRs {
        val commentToSave = commentMapper.map(commentRq)
        commentToSave.user = userRepository.findById(commentRq.userId)
            .orElseThrow { throw UserNotFoundException("User not found") }
        commentToSave.post = postRepository.findById(commentRq.postId)
            .orElseThrow { throw PostNotFoundException("Post not found") }
        return commentMapper.map(commentRepository.save(commentToSave))
    }

}