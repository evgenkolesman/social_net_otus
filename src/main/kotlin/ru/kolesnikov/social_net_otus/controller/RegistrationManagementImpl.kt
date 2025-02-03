package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.*
import ru.kolesnikov.social_net_otus.service.RegistrationManagementService

@RestController
class RegistrationManagementImpl(
    private val service: RegistrationManagementService,
) : UserManagementAndRegistrationApi {

    override fun loginPost(loginModel: LoginModel): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(service.login(loginModel))
    }

    override fun userGetIdGet(id: String): ResponseEntity<User> = ResponseEntity.ok(service.getUserById(id))


    override fun userRegisterPost(registerUserRequest: RegisterUserRequest): ResponseEntity<UserRegisterPost200Response> {
       return ResponseEntity.ok(service.userRegister(registerUserRequest))
    }

    override fun userSearchGet(firstName: String, lastName: String, like: Boolean?): ResponseEntity<List<User>> =
         when (like) {
            null, false -> {
                ResponseEntity.ok(service.userSearchGet(firstName, lastName))
            }
            else -> ResponseEntity.ok(service.userSearchGetWithLike(firstName, lastName))
        }


}
