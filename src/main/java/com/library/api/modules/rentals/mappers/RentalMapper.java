package com.library.api.modules.rentals.mappers;

import com.library.api.modules.rentals.Rental;
import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RentalMapper {
    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "renter", ignore = true)
    @Mapping(target = "isReturned", ignore = true)
    @Mapping(target = "books", ignore = true)
    Rental toEntity(RentalRequestDTO dto);

    RentalResponseDTO toResponseDto(Rental entity);

    List<RentalResponseDTO> toResponseDto(List<Rental> all);
}
