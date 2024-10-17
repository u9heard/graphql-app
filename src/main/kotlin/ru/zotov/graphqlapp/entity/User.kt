package ru.zotov.graphqlapp.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

@Entity
@Table(name = "users")
class User(
    @Column(name = "username")
    var username: String,

    @Column(name = "email")
    var email: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {

    @OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)])
    val posts: MutableSet<Post> = mutableSetOf()
}