package com.library.api.modules.rentals.mappers;

import com.library.api.modules.books.Book;
import com.library.api.modules.rentals.Rental;
import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.modules.renters.Renter;
import com.library.api.services.BookService;
import com.library.api.services.RenterService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RentalMapper {
    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    @Mapping(target = "id", ignore = true)
    Rental toEntity(RentalRequestDTO dto);

    RentalResponseDTO toResponseDto(Rental entity);

    List<RentalResponseDTO> toResponseDto(List<Rental> all);
}
