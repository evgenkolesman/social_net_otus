package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.model.LoginPost200Response
import ru.kolesnikov.social_net_otus.model.LoginPostRequest
import ru.kolesnikov.social_net_otus.model.User
import ru.kolesnikov.social_net_otus.model.UserRegisterPost200Response
import ru.kolesnikov.social_net_otus.model.UserRegisterPostRequest

@Service
class RegistrationManagementService {
    fun login(loginPostRequest: LoginPostRequest?): LoginPost200Response {
        TODO("Not yet implemented")
        return LoginPost200Response()
    }

    fun getUserById(id: String): User? {
        return null
    }

    fun userRegister(userRegisterPostRequest: UserRegisterPostRequest?): UserRegisterPost200Response? {
        return null
    }

}
