package com.sample_junk_traders

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import freemarker.cache.*
import freemarker.ext.ant.FreemarkerXmlTask
import io.ktor.freemarker.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.sessions.*
import io.ktor.features.*
import io.ktor.auth.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

data class IndexData(val items: List<Int>)

fun Application.module() {

    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing(){

        static("/static") {
            resources("static")
        }

        get("/") {
            call.respondText { "I'm Mary Poppins Y'All!" }
        }
        get("/some-stuff"){
            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }
    }
}
