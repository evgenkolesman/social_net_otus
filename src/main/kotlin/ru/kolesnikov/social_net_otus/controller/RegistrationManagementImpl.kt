package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.LoginPost200Response
import ru.kolesnikov.social_net_otus.model.LoginPostRequest
import ru.kolesnikov.social_net_otus.model.User
import ru.kolesnikov.social_net_otus.model.UserRegisterPost200Response
import ru.kolesnikov.social_net_otus.model.UserRegisterPostRequest
import ru.kolesnikov.social_net_otus.service.RegistrationManagementService

@RestController
class RegistrationManagementImpl(private val service: RegistrationManagementService) : RegistrationApi {

    override fun loginPost(loginPostRequest: LoginPostRequest?): ResponseEntity<LoginPost200Response> {
        service.login(loginPostRequest)
        return ResponseEntity.ok(LoginPost200Response())
    }

    override fun userGetIdGet(id: String): ResponseEntity<User> = ResponseEntity.ok(service.getUserById(id))


    override fun userRegisterPost(userRegisterPostRequest: UserRegisterPostRequest?):
            ResponseEntity<UserRegisterPost200Response> =
        ResponseEntity.ok(service.userRegister(userRegisterPostRequest))

}
