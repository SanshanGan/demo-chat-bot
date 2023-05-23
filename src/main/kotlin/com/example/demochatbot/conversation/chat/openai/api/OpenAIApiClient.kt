package com.example.demochatbot.conversation.chat.openai.api

import com.example.demochatbot.conversation.chat.openai.api.config.OpenAIErrorDecoderConfiguration
import com.example.demochatbot.conversation.chat.openai.api.dto.ChatCompletionDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "OpenAIApiClient",
    url = "\${services.openai.url}",
    configuration = [OpenAIErrorDecoderConfiguration::class]
)
interface OpenAIApiClient {
    @PostMapping(value = ["\${services.openai.paths.chatCompletion}"])
    fun getChatCompletion(
        @RequestHeader("Authorization") token: String,
        @RequestBody chatCompletionInfoDTO: ChatCompletionDTO.Request,
    ): ChatCompletionDTO.Response
}