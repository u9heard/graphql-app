package ru.zotov.graphqlapp.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mapstruct.factory.Mappers
import ru.zotov.graphqlapp.entity.Comment
import ru.zotov.graphqlapp.entity.Post
import ru.zotov.graphqlapp.exception.PostNotFoundException
import ru.zotov.graphqlapp.exception.UserNotFoundException
import ru.zotov.graphqlapp.mapper.BaseTest
import ru.zotov.graphqlapp.mapper.CommentMapper
import ru.zotov.graphqlapp.repository.CommentRepository
import ru.zotov.graphqlapp.repository.PostRepository
import ru.zotov.graphqlapp.repository.UserRepository
import java.util.*
import kotlin.test.assertEquals

class CommentServiceTest: BaseTest() {
    private val commentRepository: CommentRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val postRepository: PostRepository = mockk()
    private val commentMapper: CommentMapper = Mappers.getMapper(CommentMapper::class.java)

    private val commentService = CommentService(commentRepository, userRepository, postRepository, commentMapper)

    @Test
    fun `create comment`() {
        val commentRq = produceCommentRq()
        val comment = produceComment()
        val user = produceUser()
        val post = producePost()

        every { userRepository.findById(any()) } returns Optional.of(user)
        every { postRepository.findById(any()) } returns Optional.of(post)
        every { commentRepository.save(any()) } returns comment

        val result = commentService.createComment(commentRq)

        assertNotNull(result.id)
        assertEquals(result.text, commentRq.text)
        assertEquals(result.userId, commentRq.userId)
        assertEquals(result.postId, commentRq.postId)
    }

    @Test
    fun `create comment but user not found`() {
        val commentRq = produceCommentRq()
        val comment = produceComment()
        val post = producePost()

        every { userRepository.findById(any()) } returns Optional.empty()
        every { postRepository.findById(any()) } returns Optional.of(post)
        every { commentRepository.save(any()) } returns comment

        assertThrows<UserNotFoundException> { commentService.createComment(commentRq) }
    }

    @Test
    fun `create comment but post not found`() {
        val commentRq = produceCommentRq()
        val comment = produceComment()
        val user = produceUser()

        every { userRepository.findById(any()) } returns Optional.of(user)
        every { postRepository.findById(any()) } returns Optional.empty()
        every { commentRepository.save(any()) } returns comment

        assertThrows<PostNotFoundException> { commentService.createComment(commentRq) }
    }

    @Test
    fun `find comments for posts`() {
        val postsIds = listOf(1L, 2L, 3L)

        val comments = produceCommentList()

        every { commentRepository.findByPostIdIn(postsIds) } returns comments

        val result = commentService.findCommentsForPosts(postsIds)

        assertNotNull(result)
        assertEquals(result.size, 3)

        for (i in 0..2) {
            assertEquals(comments[i].id, result[i].id)
            assertEquals(comments[i].text, result[i].text)
            assertEquals(comments[i].post!!.id, result[i].postId)
            assertEquals(comments[i].user!!.id, result[i].userId)
        }
    }

    private fun produceCommentList(): List<Comment> {
        val user = produceUser()

        val comments = listOf(
            Comment("test1", 1L),
            Comment("test2", 2L),
            Comment("test3", 3L)
        )

        comments[0].post = Post(POST_TEXT, 1)
        comments[1].post = Post(POST_TEXT, 2)
        comments[2].post = Post(POST_TEXT, 3)

        comments[0].user = user
        comments[1].user = user
        comments[2].user = user

        return comments
    }
}