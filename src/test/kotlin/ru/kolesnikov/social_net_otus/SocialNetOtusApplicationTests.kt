package ru.kolesnikov.social_net_otus

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class SocialNetOtusApplicationTests {

    @Test
    fun contextLoads() {
    }

}
