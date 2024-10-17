package ru.zotov.graphqlapp.dto

data class CommentRs(
    val id: Long,
    val text: String,
    val postId: Long,
    val userId: Long
)
