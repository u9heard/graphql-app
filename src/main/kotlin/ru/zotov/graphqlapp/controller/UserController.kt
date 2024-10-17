package ru.zotov.graphqlapp.controller

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import ru.zotov.graphqlapp.dto.*
import ru.zotov.graphqlapp.service.UserService

@Controller
class UserController(
    private val userService: UserService,
) {

    @QueryMapping
    fun users() : List<UserRs> {
        return userService.findAll()
    }

    @QueryMapping
    fun userById(@Argument id: Long) : UserRs {
        return userService.getUserById(id)
    }

    @MutationMapping
    fun createUser(@Argument userRq: UserRq) : UserRs {
        return userService.addUser(userRq)
    }

    @BatchMapping(typeName = "PostRs", field = "user")
    fun userForPosts(posts: List<PostRs>) : Map<PostRs, UserRs> {
        val userMap = userService.findUsersByIds(posts.map { it.userId }).associateBy { it.id }

        return posts.associateWith { comment ->
            userMap[comment.userId] ?: throw IllegalArgumentException("User not found for comment: ${comment.id}")
        }
    }

    @BatchMapping
    fun user(comments: List<CommentRs>): Map<CommentRs, UserRs> {
        val userMap = userService.findUsersByIds(comments.map { it.userId }).associateBy { it.id }

        return comments.associateWith { comment ->
            userMap[comment.userId] ?: throw IllegalArgumentException("User not found for comment: ${comment.id}")
        }
    }
}