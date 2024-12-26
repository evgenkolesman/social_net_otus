package ru.kolesnikov.social_net_otus.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import ru.kolesnikov.social_net_otus.entity.UserRegisterEntity
import ru.kolesnikov.social_net_otus.model.User

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserRegisterEntityMapper {

    //    fun toEntity(userRegisterEntityDto: UserRegisterEntityDto): UserRegisterEntity
//
//    fun toDto(userRegisterEntity: UserRegisterEntity): UserRegisterEntityDto
    @Mapping(target = "id", source = "iden")
    fun toUser(userRegisterEntity: UserRegisterEntity): User
}