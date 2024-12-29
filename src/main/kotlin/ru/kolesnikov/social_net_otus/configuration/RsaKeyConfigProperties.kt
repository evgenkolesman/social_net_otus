package ru.kolesnikov.social_net_otus.configuration

import lombok.Getter
import lombok.Setter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Getter
@Setter
@ConfigurationProperties(prefix = "rsa")
@Component
class RsaKeyConfigProperties
 {
    lateinit var publicKey: RSAPublicKey

    lateinit var privateKey: RSAPrivateKey
}