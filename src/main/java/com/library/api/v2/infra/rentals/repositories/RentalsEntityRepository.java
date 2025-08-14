package com.library.api.v2.infra.rentals.repositories;

import com.library.api.v2.infra.rentals.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentalsEntityRepository extends JpaRepository<RentalEntity, UUID> {
}
