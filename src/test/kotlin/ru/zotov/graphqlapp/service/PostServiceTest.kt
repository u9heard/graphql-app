package ru.zotov.graphqlapp.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mapstruct.factory.Mappers
import ru.zotov.graphqlapp.exception.UserNotFoundException
import ru.zotov.graphqlapp.mapper.BaseTest
import ru.zotov.graphqlapp.mapper.PostMapper
import ru.zotov.graphqlapp.repository.PostRepository
import ru.zotov.graphqlapp.repository.UserRepository
import java.util.Optional
import kotlin.test.assertEquals

class PostServiceTest: BaseTest() {
    private val postRepository: PostRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val postMapper: PostMapper = Mappers.getMapper(PostMapper::class.java)

    private val postService = PostService(postRepository, userRepository, postMapper)

    @Test
    fun `create post`() {
        val post = producePost()
        val postRq = producePostRq()
        val owner = produceUser()

        every { postRepository.save(any()) } returns post
        every { userRepository.findById(any()) } returns Optional.of(owner)

        val createdPost = postService.createPost(postRq)

        verify(exactly = 1) { userRepository.findById(any()) }
        verify(exactly = 1) { postRepository.save(any()) }
        assertEquals(createdPost.text, post.text)
        assertEquals(createdPost.userId, post.user!!.id)
    }

    @Test
    fun `create post but user does not exist`() {
        val post = producePost()
        val postRq = producePostRq()

        every { postRepository.save(any()) } returns post
        every { userRepository.findById(any()) } returns Optional.empty()

        assertThrows<UserNotFoundException> { postService.createPost(postRq) }
    }
}