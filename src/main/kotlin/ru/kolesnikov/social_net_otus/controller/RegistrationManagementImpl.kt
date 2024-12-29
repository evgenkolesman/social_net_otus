package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.*
import ru.kolesnikov.social_net_otus.service.RegistrationManagementService

@RestController
class RegistrationManagementImpl(
    private val service: RegistrationManagementService,
) : UserManagementAndRegistrationApi {

    override fun loginPost(loginPostRequest: LoginModel): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(service.login(loginPostRequest))
    }

    override fun userGetIdGet(id: String): ResponseEntity<User> = ResponseEntity.ok(service.getUserById(id))


    override fun userRegisterPost(userRegisterPostRequest: RegisterUserRequest): ResponseEntity<UserRegisterPost200Response> {
       return ResponseEntity.ok(service.userRegister(userRegisterPostRequest))
    }

    override fun userSearchGet(firstName: String, lastName: String): ResponseEntity<List<User>> {
        return ResponseEntity.ok(service.userSearchGet(firstName, lastName))
    }

}
