package ru.zotov.graphqlapp.mapper

import ru.zotov.graphqlapp.dto.CommentRq
import ru.zotov.graphqlapp.dto.PostRq
import ru.zotov.graphqlapp.dto.UserRq
import ru.zotov.graphqlapp.entity.Comment
import ru.zotov.graphqlapp.entity.Post
import ru.zotov.graphqlapp.entity.User

open class BaseTest {
    protected val USERNAME = "username"
    protected val USER_EMAIL = "user@example.com"
    protected val POST_TEXT = "testPost"
    protected val COMMENT_TEXT = "testComment"

    protected fun produceUser(): User {
        return User(
            USERNAME,
            USER_EMAIL,
            1
        )
    }

    protected fun produceUserRq(): UserRq {
        return UserRq(
            USERNAME,
            USER_EMAIL
        )
    }

    protected fun producePost(): Post {
        val post = Post(
            POST_TEXT,
            1
        )
        post.user = produceUser()
        return post
    }

    protected fun producePostRq(): PostRq {
        return PostRq(
            1,
            POST_TEXT
        )
    }

    protected fun produceComment(): Comment {
        val comment = Comment(
            COMMENT_TEXT,
            1
        )
        comment.user = produceUser()
        comment.post = producePost()
        return comment
    }

    protected fun produceCommentRq(postId: Long = 1, userId: Long = 1): CommentRq {
        return CommentRq(
            COMMENT_TEXT,
            userId,
            postId
        )
    }
}