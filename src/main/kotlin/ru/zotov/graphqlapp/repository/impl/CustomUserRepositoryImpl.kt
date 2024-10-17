package ru.zotov.graphqlapp.repository.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import ru.zotov.graphqlapp.entity.User
import ru.zotov.graphqlapp.repository.CustomUserRepository

@Repository
class CustomUserRepositoryImpl : CustomUserRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findCustomUser(id:Long): User? {
        val query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id")
        query.setParameter("id", id)
        return query.singleResult as User?
    }

}