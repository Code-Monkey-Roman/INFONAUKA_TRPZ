package com.barchuk.springbootinfoscience;

import com.barchuk.springbootinfoscience.model.Organization;
import com.barchuk.springbootinfoscience.repository.ConferenceRepository;
import com.barchuk.springbootinfoscience.repository.OrganizationRepository;
import com.barchuk.springbootinfoscience.service.ConferenceService;
import com.barchuk.springbootinfoscience.model.Conference;
import com.barchuk.springbootinfoscience.service.OrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootconferenceApplication.class)
@Transactional
public class RepositoryTest {

    @Autowired
    private ConferenceRepository genericEntityRepository;
    @Autowired
    private ConferenceService serv;

    @Autowired
    private OrganizationRepository orgRepo;
    @Autowired
    private OrganizationService orgServ;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        Conference genericEntity = genericEntityRepository
                .save(new Conference(6L,"запит 1","Dnipro", LocalDate.now())); //(6,'2021-02-07 21:09:00','Dnipro','HTTP')
   genericEntityRepository
                .save(new Conference(7L,"запит 2","Dnipro", LocalDate.now()));

        assertEquals(genericEntityRepository.findAll().size(), 2);
        System.out.println(genericEntityRepository.findAll());

        assertEquals(genericEntityRepository.findAll().size(), 2);
        System.out.println(genericEntityRepository.findAll());
    }

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetrieveEntity() {
        System.out.println(genericEntityRepository.findAll());
    }

    @Test
    public void ServiceShouldSaveAndDeleteByID(){
        Organization orgEntity = orgServ.saveOrganization(new Organization(6L,"infonauka",
                122354,"Dragomanov","Vitrenko", "Dnipro"));
        orgServ.saveOrganization(new Organization(7L,"kpi",122854,
                "Zhurovsky","Didenko","kyiv"));

        assertEquals(orgServ.findAll().size(), 2);
        System.out.println(orgServ.findAll());

        assertEquals(orgServ.findAll().size(), 2);
        System.out.println(orgServ.findAll());
    }


}