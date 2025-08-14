package com.library.api.v2.infra.renters.repositories;

import com.library.api.v2.infra.renters.RenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RenterEntityRepository extends JpaRepository<RenterEntity, UUID> {
}
