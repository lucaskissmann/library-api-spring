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
    Renter toEntity(RenterRequestDTO dto);

    RenterResponseDTO toResponseDto(Renter renter);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Renter renter, UpdateRenterDTO updateDto);

    List<RenterResponseDTO> toResponseDto(List<Renter> renters);
}
