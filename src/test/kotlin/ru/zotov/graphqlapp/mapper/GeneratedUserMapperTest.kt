package ru.zotov.graphqlapp.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class GeneratedUserMapperTest: BaseTest() {

    private val userMapper = Mappers.getMapper(UserMapper::class.java)

    @Test
    fun `mapping User to UserRs`() {
        val user = produceUser()

        val userRs = userMapper.map(user)

        assertEquals(user.id, userRs.id)
        assertEquals(user.username, userRs.username)
        assertEquals(user.email, userRs.email)
    }

    @Test
    fun `mapping UserRq to User`() {
        val userRq = produceUserRq()

        val user = userMapper.map(userRq)

        assertNull(user.id)
        assertEquals(user.posts.size, 0)
        assertEquals(user.username, userRq.username)
        assertEquals(user.email, userRq.email)
    }
}