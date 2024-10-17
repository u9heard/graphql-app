package ru.zotov.graphqlapp.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mapstruct.factory.Mappers
import ru.zotov.graphqlapp.exception.UserNotFoundException
import ru.zotov.graphqlapp.mapper.BaseTest
import ru.zotov.graphqlapp.mapper.PostMapper
import ru.zotov.graphqlapp.mapper.UserMapper
import ru.zotov.graphqlapp.repository.UserRepository
import java.util.*

class UserServiceTest: BaseTest() {
    private val userRepository: UserRepository = mockk()
    private val userService: UserService = UserService(
        userRepository,
        Mappers.getMapper(UserMapper::class.java),
        Mappers.getMapper(PostMapper::class.java)
    )

    @Test
    fun `find user by id`() {
        val user = produceUser()
        every { userRepository.findById(any()) } returns Optional.of(user)

        val result = userService.getUserById(1)

        verify(exactly = 1) { userRepository.findById(1) }
        assertEquals(user.id, result.id)
        assertEquals(user.email, result.email)
        assertEquals(user.username, result.username)
    }

    @Test
    fun `find user by id not found`() {
        every { userRepository.findById(any()) } returns Optional.empty()

        assertThrows<UserNotFoundException> { userService.getUserById(1) }
    }

    @Test
    fun `find user by email`() {
        val user = produceUser()
        every { userRepository.findByEmail(any()) } returns user

        val result = userService.getUserByEmail(USER_EMAIL)

        assertEquals(user.id, result.id)
        assertEquals(user.email, result.email)
        assertEquals(user.username, result.username)
    }

    @Test
    fun `find user by email not found`() {
        every { userRepository.findByEmail(any()) } returns null

        assertThrows<UserNotFoundException> { userService.getUserByEmail(USER_EMAIL) }
    }

    @Test
    fun `add user`() {
        val userRq = produceUserRq()
        val user = produceUser()
        every { userRepository.save(any()) } returns user

        userService.addUser(userRq)

        verify(exactly = 1) { userRepository.save(any()) }
    }
}