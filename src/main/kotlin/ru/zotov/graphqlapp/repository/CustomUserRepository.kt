package ru.zotov.graphqlapp.repository

import ru.zotov.graphqlapp.entity.User

interface CustomUserRepository {
    fun findCustomUser(id: Long) : User?
}