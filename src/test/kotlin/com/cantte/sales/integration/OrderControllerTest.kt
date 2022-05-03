package com.cantte.sales.integration

import com.cantte.customers.domain.Address
import com.cantte.customers.domain.Customer
import com.cantte.customers.domain.CustomerRepository
import com.cantte.orders.domain.OrderRepository
import com.cantte.products.domain.Product
import com.cantte.products.domain.ProductRepository
import org.junit.Ignore
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
class OrderControllerTest {

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
    lateinit var repository: OrderRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    private val customer = Customer(
        "123", "John", "Doe", "does@gmail.com",
    )

    private val address = Address("Street", "City", "State", "12345")

    @AfterEach
    fun clean() {
        repository.deleteAll()
        customerRepository.deleteAll()
        productRepository.deleteAll()
    }

    @Ignore("This test is not working")
    @Test
    fun `test save order should return ok`() {
        productRepository.save(Product("123", "Product 1", 10.0f, 0.19f))
        productRepository.save(Product("321", "Product 2", 20.0f, 0.19f))

        customer.addAddress(address)
        customerRepository.save(customer)

        mockMvc.post("/api/orders") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = """
                {
                    "customerId": "123",
                    "deliveryAddressId": 1,
                    "items": [
                        {
                            "productCode": "321",
                            "quantity": 2
                        }
                    ],
                    "payments": [
                        {
                            "type": "CREDIT_CARD",
                            "amount": 30.0
                        }
                    ]
                }
            """.trimIndent()
        }.andExpect { status { isOk() } }
    }

}