package ru.zotov.graphqlapp.controller

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import ru.zotov.graphqlapp.dto.CommentRq
import ru.zotov.graphqlapp.dto.CommentRs
import ru.zotov.graphqlapp.dto.PostRs
import ru.zotov.graphqlapp.service.CommentService

@Controller
class CommentController(
    private val commentService: CommentService
) {

    @BatchMapping
    fun commentsByPost(posts: List<PostRs>) : Map<PostRs, List<CommentRs>> {
        return commentService.findCommentsForPosts(posts.map { it.id })
            .groupBy {
                posts.find { postRs: PostRs -> postRs.id == it.postId }!!
            }
    }

    @MutationMapping
    fun createComment(@Argument commentRq: CommentRq) : CommentRs {
        return commentService.createComment(commentRq)
    }
}