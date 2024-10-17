package ru.zotov.graphqlapp.entity

import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment(
    @Column(name = "text")
    val text: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post? = null
}