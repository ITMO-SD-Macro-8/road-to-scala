package com.itmo.microservices.demo.delivery.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @Transactional
    internal fun `should reject on nearest slot call`() {
        val date = LocalDate.of(2020, 1, 12)

        mockMvc.post("/delivery/getTimeSlot") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(date)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }.andReturn()
    }

}