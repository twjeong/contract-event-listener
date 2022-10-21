package com.mosaicsquare.blockchain

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class InfoController() {
    @Value("\${spring.config.activate.on-profile}")
    lateinit var profile: String

    @Value("\${version}")
    lateinit var version: String

    @GetMapping("/")
    fun entry(): String {
        return "redirect:info"
    }

    @GetMapping("/info")
    fun info(): ResponseEntity<Info> {
        return ResponseEntity.ok(Info(profile, version))
    }

    data class Info(val profile: String, val version: String)
}
