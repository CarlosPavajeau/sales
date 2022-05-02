package com.cantte.sales.integration

import com.cantte.products.domain.Product
import com.cantte.products.domain.ProductRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
class ProductControllerTest {

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
    lateinit var repository: ProductRepository

    @AfterEach
    fun cleanUp() {
        repository.deleteAll()
    }

    @Test
    fun `test save product should return ok`() {
        mockMvc.post("/products") {
            content = """
                {
                    "code": "123",
                    "name": "Product 1",
                    "price": 10.0,
                    "tax": 0.0
                }
            """.trimIndent()
        }.andExpect { status { isCreated() } }
    }

    @Test
    fun `test search all products should return ok`() {
        repository.save(Product(code = "123", name = "Product 1", price = 10.0f, tax = 0.0f))

        mockMvc.get("/products").andExpect {
            status { isOk() }
        }
    }

}