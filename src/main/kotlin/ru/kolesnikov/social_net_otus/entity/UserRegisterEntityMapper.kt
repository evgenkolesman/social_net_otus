package ru.kolesnikov.social_net_otus.entity

import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import ru.kolesnikov.social_net_otus.model.User

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface UserRegisterEntityMapper {

    fun toEntity(userRegisterEntityDto: UserRegisterEntityDto): UserRegisterEntity

    fun toDto(userRegisterEntity: UserRegisterEntity): UserRegisterEntityDto
    fun toUser(userRegisterEntity: UserRegisterEntity): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun partialUpdate(
        userRegisterEntityDto: UserRegisterEntityDto,
        @MappingTarget userRegisterEntity: UserRegisterEntity,
    ): UserRegisterEntity
}