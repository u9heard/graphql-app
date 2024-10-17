package ru.zotov.graphqlapp.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import ru.zotov.graphqlapp.dto.PostRq
import ru.zotov.graphqlapp.dto.PostRs
import ru.zotov.graphqlapp.entity.Post

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PostMapper {
    @Mapping(target = "userId", source = "user.id")
    fun map(post: Post): PostRs
    fun map(post: PostRq): Post
}