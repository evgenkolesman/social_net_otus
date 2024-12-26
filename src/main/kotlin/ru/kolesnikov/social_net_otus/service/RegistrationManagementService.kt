package ru.kolesnikov.social_net_otus.service

import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest
import org.springframework.security.authentication.ott.OneTimeTokenService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.entity.UserRegisterEntity
import ru.kolesnikov.social_net_otus.model.LoginPost200Response
import ru.kolesnikov.social_net_otus.model.LoginPostRequest
import ru.kolesnikov.social_net_otus.model.User
import ru.kolesnikov.social_net_otus.model.UserRegisterPost200Response
import ru.kolesnikov.social_net_otus.model.UserRegisterPostRequest
import ru.kolesnikov.social_net_otus.repository.UserRegisterRepository
import java.util.*

@Service
class RegistrationManagementService(
    private val userDetailsManager: UserDetailsManager,
    private val userRegisterRepository: UserRegisterRepository,
    private val oneTimeTokenService: OneTimeTokenService
) {
    fun login(loginPostRequest: LoginPostRequest?): LoginPost200Response {
        if (userDetailsManager.userExists(loginPostRequest?.id)) {
            val loadUserByUsername = userDetailsManager.loadUserByUsername(loginPostRequest?.id)

            if (loadUserByUsername.isAccountNonExpired) {
                val token = oneTimeTokenService.generate(GenerateOneTimeTokenRequest(loginPostRequest?.id))
                return LoginPost200Response(token.tokenValue)
            }
        }
        return LoginPost200Response()
    }

    fun getUserById(id: String): User? {
        val entityOpt = userRegisterRepository.findById(UUID.fromString(id))
        return if (entityOpt.isPresent) {
            val user = entityOpt.get()
            return User(user.iden.toString(), user.firstName, user.secondName, user.birthdate, user.biography, user.city)
        } else null
    }

    fun userRegister(userRegisterPostRequest: UserRegisterPostRequest?): UserRegisterPost200Response? {
        val id = UUID.randomUUID()
        val userDetails = org.springframework.security.core.userdetails.User(
            id.toString(),
            userRegisterPostRequest?.password,
            listOf(SimpleGrantedAuthority("USER"))
        )
        val userEntity = UserRegisterEntity(
            null,
            userDetails.username,
            userRegisterPostRequest?.firstName,
            userRegisterPostRequest?.secondName,
            userRegisterPostRequest?.birthdate,
            userRegisterPostRequest?.biography,
            userRegisterPostRequest?.city,
            userDetails.password
        )
        val user = userRegisterRepository.save(userEntity)

        userDetailsManager.createUser(userDetails)
        return UserRegisterPost200Response(id.toString())
    }

}
