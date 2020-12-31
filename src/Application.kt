package com.sample_junk_traders

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.cio.websocket.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kweb.*
import kweb.state.KVar
import kweb.state.render

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

data class IndexData(val items: List<Int>)

fun Application.module() {

    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(WebSockets) {
        pingPeriod = java.time.Duration.ofSeconds(10)
        timeout = java.time.Duration.ofSeconds(30)
    }

    install(Kweb)

    routing(){

        static("/static") {
            resources("static")
        }

        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf<String, Int>()))
        }

        get("/kweb") {
            call.respondKweb {
                doc.body {
                    h1().text("I'm Mary Poppins Y'All!")
                    p().text("What is your name?")
                    val textField = input()
                    val name = KVar("")
                    textField.value = name
                    render(name) {
                        if (name.value == "") {
                            p().text("Enter a name")
                        } else {
                            p().text(name.map { "Hello $it" })
                        }
                    }
                }
            }
        }

        get("/delayr") {
            val name = KVar("Loading")
            GlobalScope.launch {
                delay(2000L)
                name.value = "Mary Poppins"
            }
//            val name = KVar("Loading")
//            GlobalScope.launch {
//                delay(3000L)
//                name.value = "Mary Poppins"
//            }
            call.respond(FreeMarkerContent("delayed.ftl", mapOf("message" to name)))
//            call.respondKweb {
//                doc.body {
//                    h1().text(name)
//                }
//            }
        }

        get("/some-stuff"){
            call.respond(FreeMarkerContent("someStuff.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }

        get("/texty") {
            call.respondText { "I'm Mary Poppins Y'All!" }
        }
    }
}
