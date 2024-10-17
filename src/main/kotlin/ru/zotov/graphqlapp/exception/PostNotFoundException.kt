package ru.zotov.graphqlapp.exception

class PostNotFoundException(override val message: String = "Post not found") : RuntimeException() {

}