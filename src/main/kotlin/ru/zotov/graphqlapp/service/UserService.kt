package ru.zotov.graphqlapp.service

import org.springframework.stereotype.Service
import ru.zotov.graphqlapp.dto.UserRq
import ru.zotov.graphqlapp.dto.UserRs
import ru.zotov.graphqlapp.exception.UserNotFoundException
import ru.zotov.graphqlapp.mapper.PostMapper
import ru.zotov.graphqlapp.mapper.UserMapper
import ru.zotov.graphqlapp.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val postMapper: PostMapper
    ) {

    fun getUserById(id:Long) : UserRs {
        val user = userRepository.findById(id)
            .orElseThrow { throw UserNotFoundException("User not found") }
        return userMapper.map(user)
    }

    fun getUserByEmail(email:String) : UserRs {
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException("User not found")

        return userMapper.map(user)
    }

    fun addUser(userRq: UserRq) : UserRs {
        val userToSave = userMapper.map(userRq)
        return userMapper.map(userRepository.save(userToSave))
    }

    fun findAll() : List<UserRs> {
        return userRepository.findAll()
            .map { userMapper.map(it) }
            .toList()
    }

    fun findUsersByIds(ids:List<Long>) : List<UserRs> {
        return userRepository.findAllById(ids)
            .map { userMapper.map(it) }
    }
}