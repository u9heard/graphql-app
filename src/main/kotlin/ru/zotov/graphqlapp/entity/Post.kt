package ru.zotov.graphqlapp.entity

import jakarta.persistence.*
import jakarta.persistence.Entity


@Entity
@Table(name = "posts")
class Post(
    @Column(name = "text")
    var text: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    var comments: MutableList<Comment> = mutableListOf()
}
