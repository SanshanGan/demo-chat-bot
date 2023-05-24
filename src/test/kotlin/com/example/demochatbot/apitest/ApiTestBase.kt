package com.example.demochatbot.apitest

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest()
@AutoConfigureWireMock(stubs = ["classpath:/wiremock-stub"], port = 0)
class ApiTestBase {
}