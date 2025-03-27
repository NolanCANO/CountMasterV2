package com.example.demo.user.mapper;

import com.example.demo.user.model.User;
import com.example.demo.user.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "passwordHash", ignore = true)
    User toEntity(UserDTO dto);

    @Mapping(target = "passwordHash", ignore = true)
    void updateEntityFromDTO(UserDTO dto, @MappingTarget User user);
}
