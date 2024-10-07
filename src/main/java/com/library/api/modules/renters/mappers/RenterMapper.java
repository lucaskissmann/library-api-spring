package com.library.api.modules.renters.mappers;

import com.library.api.modules.authors.enums.Genders;
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
    @Mapping(target = "phone", source = "dto.phone", qualifiedByName = "cleanPhone")
    Renter toEntity(RenterRequestDTO dto);

    @Mapping(target = "cpf", source = "renter.cpf", qualifiedByName = "cleanCpf")
    @Mapping(target = "phone", source = "renter.phone", qualifiedByName = "cleanPhone")
    RenterResponseDTO toResponseDto(Renter renter);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "cpf", source = "updateDto.cpf", qualifiedByName = "cleanCpf")
    @Mapping(target = "phone", source = "updateDto.phone", qualifiedByName = "cleanPhone")
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

    @Named("cleanPhone")
    default String cleanPhone(String phone) {
        if (phone != null && !phone.isEmpty()) {
            return phone.replaceAll("\\D", "");
        }
        return null;
    }

    default Genders mapGender(String gender) {
        if (gender == null) {
            return null;
        }
        return Enum.valueOf(Genders.class, gender.toUpperCase());
    }
}
