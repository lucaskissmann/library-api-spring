package com.library.api.repositories;

import com.library.api.modules.authors.Author;
import com.library.api.modules.renters.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RenterRepository extends JpaRepository<Renter, Long> {
    Optional<Renter> findByCpf(String cpf);

    Optional<Renter> findByEmail(String email);
}
