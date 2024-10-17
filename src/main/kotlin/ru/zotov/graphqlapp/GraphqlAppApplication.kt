package ru.zotov.graphqlapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlAppApplication

fun main(args: Array<String>) {
    runApplication<GraphqlAppApplication>(*args)
}
