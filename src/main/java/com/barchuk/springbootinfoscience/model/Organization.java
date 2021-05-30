package com.barchuk.springbootinfoscience.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "org_name", nullable=false)
    private String orgName;

    @Column(name = "paymentno", nullable = false)
    private int payment;

    @Column(name = "director", nullable=false)
    private String dirName;

    @Column(name = "accountant", nullable=false)
    private String accName;

    @Column(name = "adress", nullable=false)
    private String location;

    public Organization(Long id, String orgName, int payment, String dirName, String accName, String location) {
        this.id = id;
        this.orgName = orgName;
        this.payment = payment;
        this.dirName = dirName;
        this.accName = accName;
        this.location = location;
    }

    public Organization() {
    }
}
