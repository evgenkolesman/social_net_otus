package ru.kolesnikov.social_net_otus.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import ru.kolesnikov.social_net_otus.entity.DialogsManagementEntity
import ru.kolesnikov.social_net_otus.model.DialogsManagementEntityDto

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface DialogsManagementEntityMapper {

    fun toEntity(dialogsManagementEntityDto: DialogsManagementEntityDto): DialogsManagementEntity

    fun toDto(dialogsManagementEntity: DialogsManagementEntity): DialogsManagementEntityDto
}