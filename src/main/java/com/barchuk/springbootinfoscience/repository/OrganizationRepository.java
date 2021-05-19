package com.barchuk.springbootinfoscience.repository;

import com.barchuk.springbootinfoscience.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
