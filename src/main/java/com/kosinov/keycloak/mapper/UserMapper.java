package com.kosinov.keycloak.mapper;

import com.kosinov.keycloak.dto.UserDTO;
import com.kosinov.keycloak.dto.UserUpdateDTO;
import com.kosinov.keycloak.model.User;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO userDTO);


    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    void update(UserUpdateDTO updateDTO, @MappingTarget User employee);
}
