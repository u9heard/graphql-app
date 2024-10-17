package ru.zotov.graphqlapp.dto

data class CommentRq(
    val text: String,
    val userId: Long,
    val postId: Long
)