package ru.zotov.graphqlapp.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class GeneratedPostMapperTest: BaseTest() {
    private val mapper = Mappers.getMapper(PostMapper::class.java)

    @Test
    fun `map Post to PostRs`() {
        val post = producePost()

        val postRs = mapper.map(post)

        assertEquals(postRs.id, post.id)
        assertEquals(postRs.text, post.text)
        assertEquals(postRs.userId, post.user!!.id)
    }

    @Test
    fun `map PostRq to Post`() {
        val postRq = producePostRq()

        val post = mapper.map(postRq)

        assertNull(post.id)
        assertNull(post.user)
        assertEquals(post.comments.size, 0)
        assertEquals(post.text, postRq.text)
    }
}