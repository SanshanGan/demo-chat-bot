package com.example.demochatbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class DemoChatBotApplication

fun main(args: Array<String>) {
	runApplication<DemoChatBotApplication>(*args)
}
