package com.library.api.modules.renters.mappers;

import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RenterMapper {
    RenterMapper INSTANCE = Mappers.getMapper(RenterMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "cpf", source = "dto.cpf", qualifiedByName = "cleanCpf")
    Renter toEntity(RenterRequestDTO dto);

    @Mapping(target = "cpf", source = "renter.cpf", qualifiedByName = "cleanCpf")
    RenterResponseDTO toResponseDto(Renter renter);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "cpf", source = "updateDto.cpf", qualifiedByName = "cleanCpf")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Renter renter, UpdateRenterDTO updateDto);

    List<RenterResponseDTO> toResponseDto(List<Renter> renters);

    @Named("cleanCpf")
    default String cleanCpf(String cpf) {
        if (cpf != null && !cpf.isEmpty()) {
            return cpf.replaceAll("\\D", "");
        }
        return null;
    }
}
