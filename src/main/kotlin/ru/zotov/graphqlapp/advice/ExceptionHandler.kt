package ru.zotov.graphqlapp.advice

import graphql.GraphQLError
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.graphql.execution.ErrorType
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.zotov.graphqlapp.exception.PostNotFoundException
import ru.zotov.graphqlapp.exception.UserNotFoundException


@ControllerAdvice
class ExceptionHandler {

    @GraphQlExceptionHandler(UserNotFoundException::class, PostNotFoundException::class)
    fun handleNotFound(ex: RuntimeException): GraphQLError {
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST).message(ex.message).build()
    }
}