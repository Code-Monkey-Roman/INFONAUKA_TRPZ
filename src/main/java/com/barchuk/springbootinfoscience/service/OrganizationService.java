package com.barchuk.springbootinfoscience.service;

import com.barchuk.springbootinfoscience.model.Organization;
import com.barchuk.springbootinfoscience.model.Presentation;
import com.barchuk.springbootinfoscience.repository.OrganizationRepository;
import com.barchuk.springbootinfoscience.repository.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {
    final private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization findById(Long id) {
        return organizationRepository.getOne(id);
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Page<Organization> findAllByNo(int number, Pageable pageable) {
        Page<Organization> pageOfOrganizations = organizationRepository.findAllByPayment(number, pageable);
        return pageOfOrganizations;
    }

    public Organization saveOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }
}
