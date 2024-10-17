package ru.zotov.graphqlapp.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import ru.zotov.graphqlapp.dto.CommentRq
import ru.zotov.graphqlapp.dto.CommentRs
import ru.zotov.graphqlapp.entity.Comment

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface CommentMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    fun map(comment: Comment): CommentRs

//    @Mapping(source = "postId", target = "post.id")
//    @Mapping(source = "userId", target = "user.id")
    fun map(commentRq: CommentRq): Comment
}