package ru.zotov.graphqlapp.service

import org.springframework.stereotype.Service
import ru.zotov.graphqlapp.dto.PostRq
import ru.zotov.graphqlapp.dto.PostRs
import ru.zotov.graphqlapp.entity.Post
import ru.zotov.graphqlapp.entity.User
import ru.zotov.graphqlapp.exception.PostNotFoundException
import ru.zotov.graphqlapp.exception.UserNotFoundException
import ru.zotov.graphqlapp.mapper.PostMapper
import ru.zotov.graphqlapp.repository.PostRepository
import ru.zotov.graphqlapp.repository.UserRepository

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val postMapper: PostMapper,
) {

    fun findById(id: Long): PostRs {
        val post = postRepository.findById(id)
            .orElseThrow { throw PostNotFoundException() }
        return postMapper.map(post)
    }

    fun createPost(postRq: PostRq): PostRs {
        val owner: User = userRepository.findById(postRq.userId)
            .orElseThrow { throw UserNotFoundException("User not found") }
        val newPost = postMapper.map(postRq)
        newPost.user = owner
        return postMapper.map(postRepository.save(newPost))
    }

    fun findPostsForUserIn(userIds: List<Long>): Set<PostRs> {
        return postRepository.findByUserIdIn(userIds)
            .map { post: Post -> postMapper.map(post) }
            .toSet()
    }
}