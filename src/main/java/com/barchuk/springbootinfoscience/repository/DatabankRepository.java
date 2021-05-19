package com.barchuk.springbootinfoscience.repository;

import com.barchuk.springbootinfoscience.model.Databank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DatabankRepository extends JpaRepository<Databank, Long> {
    //Optional<Databank> findByName(String name);
}
