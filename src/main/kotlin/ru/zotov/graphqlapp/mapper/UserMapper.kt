package ru.zotov.graphqlapp.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel
import ru.zotov.graphqlapp.dto.UserRq
import ru.zotov.graphqlapp.dto.UserRs
import ru.zotov.graphqlapp.entity.User

@Mapper(componentModel = ComponentModel.SPRING)
interface UserMapper {
    fun map(user: User) : UserRs
    fun map(userRq: UserRq) : User
}