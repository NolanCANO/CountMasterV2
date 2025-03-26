package com.example.demo.depense.mapper;

import com.example.demo.depense.model.Depense;
import com.example.demo.depense.model.DepenseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepenseMapper {
    DepenseDTO toDTO(Depense depense);
    Depense toEntity(DepenseDTO dto);
    void updateEntityFromDTO(DepenseDTO dto, @MappingTarget Depense depense);
}