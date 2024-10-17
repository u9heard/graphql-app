package ru.zotov.graphqlapp.mapper

import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GeneratedCommentMapperTest: BaseTest() {
    private val commentMapper: CommentMapper = Mappers.getMapper(CommentMapper::class.java)

    @Test
    fun `map Comment to CommentRs`() {
        val comment = produceComment()

        val commentRs = commentMapper.map(comment)

        assertEquals(commentRs.id, comment.id)
        assertEquals(commentRs.text, comment.text)
        assertEquals(commentRs.userId, comment.user!!.id)
        assertEquals(commentRs.postId, comment.post!!.id)
    }

    @Test
    fun `map CommentRq to Comment`() {
        val commentRq = produceCommentRq()

        val comment = commentMapper.map(commentRq)

        assertNull(comment.id)
        assertNull(comment.post)
        assertNull(comment.user)
        assertEquals(comment.text, comment.text)
    }
}