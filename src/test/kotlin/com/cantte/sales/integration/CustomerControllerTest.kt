package com.cantte.sales.integration

import com.cantte.customers.domain.Customer
import com.cantte.customers.domain.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity.notFound
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
class CustomerControllerTest {

    companion object {
        @Container
        val mysql: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:8")).withExposedPorts(3306)

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            mysql.start()

            registry.add("spring.datasource.url", mysql::getJdbcUrl)
            registry.add("spring.datasource.password", mysql::getPassword)
            registry.add("spring.datasource.username", mysql::getUsername)
        }
    }

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @AfterEach
    fun cleanUp() {
        customerRepository.deleteAll()
    }

    @Test
    fun `test save customer should return ok`() {
        mockMvc.post("/customers") {
            content = """
                {
                    "id": "123",
                    "name": "John",
                    "lastName": "Doe",
                    "email": "does@jhon.com"
                }
            """.trimIndent()
        }.andExpect { status { isCreated() } }
    }

    @Test
    fun `test find customer should return not found`() {
        mockMvc.get("/customers/123").andExpect {
            status { notFound() }
        }
    }

    @Test
    fun `test find customer should return ok`() {
        customerRepository.save(
            Customer(
                id = "123",
                name = "John",
                lastName = "Doe",
                email = "does@jhon.com",
            )
        )

        mockMvc.get("/customers/123").andExpect {
            status { isOk() }
            content {
                json(
                    """
                        {
                            "name": "John",
                            "lastName": "Doe",
                            "email": "does@jhon.com"
                        }
                    """.trimIndent()
                )
            }
        }
    }
}