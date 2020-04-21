package com.sample

import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

val app = application(WebApplicationType.REACTIVE) {
  webFlux {
    port = 8080
    router {
      val service = SampleService()
      val handler = SampleHandler(service)
      GET("/", handler::hello)
      GET("/api", handler::json)
    }
    codecs {
      string()
      jackson()
    }
  }
}

data class Sample(val message: String)

class SampleService {
  fun generateMessage(): String = "Hello world!"
}


class SampleHandler(private val sampleService: SampleService) {
  @Suppress("UNUSED_PARAMETER")
  fun hello(request: ServerRequest): Mono<ServerResponse> =
    ok().bodyValue(sampleService.generateMessage())

  @Suppress("UNUSED_PARAMETER")
  fun json(request: ServerRequest): Mono<ServerResponse> =
    ok().bodyValue(Sample(sampleService.generateMessage()))
}

fun main() {
  app.run()
}
