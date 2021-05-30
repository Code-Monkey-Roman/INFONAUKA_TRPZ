package com.barchuk.springbootinfoscience.repository;

import com.barchuk.springbootinfoscience.model.Organization;
import com.barchuk.springbootinfoscience.model.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Page<Organization> findAllByPayment(int payment, Pageable pageable);
}
