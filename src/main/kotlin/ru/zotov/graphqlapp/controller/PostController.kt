package ru.zotov.graphqlapp.controller

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import ru.zotov.graphqlapp.dto.PostRq
import ru.zotov.graphqlapp.dto.PostRs
import ru.zotov.graphqlapp.dto.UserRs
import ru.zotov.graphqlapp.service.PostService

@Controller
class PostController(
    private val postService: PostService,
) {

    @QueryMapping
    fun postById(@Argument id: Long): PostRs {
        return postService.findById(id)
    }

    @MutationMapping
    fun createPost(@Argument postRq: PostRq): PostRs {
        return postService.createPost(postRq)
    }

    @BatchMapping
    fun postsByUser(users: List<UserRs>): Map<UserRs, List<PostRs>> {
        return postService.findPostsForUserIn(users.map { it.id })
            .groupBy {
                users.find { userRs: UserRs -> userRs.id == it.userId }!!
            }
    }
}