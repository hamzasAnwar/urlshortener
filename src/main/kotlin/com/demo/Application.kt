package com.demo

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info


@OpenAPIDefinition(
    info = Info(
        title = "urlshortner",
        version = "0.0",
        description = "Url Shorten Service",
    )
)
object ApplicationKt {
    // ApplicationKt.context can be used to get properly set up beans in classes were it usually doesn't work, e.g. kotlin extensions
    lateinit var context: ApplicationContext

    @JvmStatic
    fun main(args: Array<String>) {
        context = Micronaut.build()
            .args(*args)
            .start()
    }
}
