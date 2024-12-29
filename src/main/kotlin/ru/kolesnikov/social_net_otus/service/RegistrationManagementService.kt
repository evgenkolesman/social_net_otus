package ru.kolesnikov.social_net_otus.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.entity.UserRegisterEntity
import ru.kolesnikov.social_net_otus.model.LoginPost200Response
import ru.kolesnikov.social_net_otus.model.LoginPostRequest
import ru.kolesnikov.social_net_otus.model.User
import ru.kolesnikov.social_net_otus.model.UserRegisterPost200Response
import ru.kolesnikov.social_net_otus.model.UserRegisterPostRequest
import ru.kolesnikov.social_net_otus.repository.UserRegisterRepository
import javax.security.auth.login.LoginException


@Service
class RegistrationManagementService(
    private val userDetailsManager: UserDetailsManager,
    private val userRegisterRepository: UserRegisterRepository,
    private val authService: AuthService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val currentLoginProvider: CurrentLoginProvider

    ) {
    fun login(loginPostRequest: LoginPostRequest): LoginPost200Response {
        if (loginPostRequest.password.isNullOrBlank()
            || loginPostRequest.id.isNullOrBlank()
        ) throw LoginException("This data cannot be null or empty")

        val auth = UsernamePasswordAuthenticationToken(loginPostRequest.id, loginPostRequest.password)
        val authenticate = authenticationManager.authenticate(auth)
        val generateToken = authService.generateToken(authenticate)
        return LoginPost200Response(generateToken)
    }

    fun getUserById(id: String): User {
        println(currentLoginProvider.getCurrentLogin())

        val entityOpt = userRegisterRepository.findByUsername(id)
        return if (entityOpt.isPresent) {
            val user = entityOpt.get()
            return User(
                user.username,
                user.firstName,
                user.secondName,
                user.birthdate,
                user.biography,
                user.city
            )
        } else User()
    }

    fun userRegister(userRegisterPostRequest: UserRegisterPostRequest): UserRegisterPost200Response? {
        return if (userRegisterPostRequest.userId.isNullOrBlank() || userRegisterPostRequest.password.isNullOrBlank()) {
            throw RegistrationException("Login and password cannot be blank!!!")
        } else {
            val password = passwordEncoder.encode(userRegisterPostRequest.password)
            val userDetails =
                org.springframework.security.core.userdetails.User(
                    /* username = */ userRegisterPostRequest.userId,
                    /* password = */ password,
                    /* authorities = */ listOf(SimpleGrantedAuthority("USER"))
                )
            val userEntity = UserRegisterEntity(
                null,
                userDetails.username,
                userRegisterPostRequest.firstName,
                userRegisterPostRequest.secondName,
                userRegisterPostRequest.birthdate,
                userRegisterPostRequest.biography,
                userRegisterPostRequest.city,
                password
            )
            val user = userRegisterRepository.save(userEntity)

            userDetailsManager.createUser(userDetails)
            UserRegisterPost200Response(user.username)
        }
    }

}

class RegistrationException(message: String) : RuntimeException(message)
