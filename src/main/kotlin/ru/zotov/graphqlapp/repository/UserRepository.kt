package ru.zotov.graphqlapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zotov.graphqlapp.entity.User

interface UserRepository : JpaRepository<User, Long>, CustomUserRepository {
    fun findByEmail(email: String): User?
}